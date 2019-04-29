/**
 * 
 */
package ml.bootcode.profileapp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

/**
 * Utility class for streaming multipart files.
 * 
 * @author sunnyb
 *
 */
public class MultipartFileSender {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final int DEFAULT_BUFFER_SIZE = 131072; // ..bytes = 128KB.

	private static final long DEFAULT_EXPIRE_TIME = 604800000L; // ..ms = 1 week.

	private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";

	Path path;
	HttpServletRequest request;
	HttpServletResponse response;

	/**
	 * Send multipart file from path.
	 * 
	 * @param path
	 * @return
	 */
	public static MultipartFileSender fromPath(Path path) {
		return new MultipartFileSender().setPath(path);
	}

	/**
	 * Send multipart file from file object.
	 * 
	 * @param file
	 * @return
	 */
	public static MultipartFileSender fromFile(File file) {
		return new MultipartFileSender().setPath(file.toPath());
	}

	/**
	 * Send multipart file from resource URI.
	 * 
	 * @param uri
	 * @return
	 */
	public static MultipartFileSender fromUri(String uri) {
		return new MultipartFileSender().setPath(Paths.get(uri));
	}

	/**
	 * Internal path setter.
	 * 
	 * @param path
	 * @return
	 */
	private MultipartFileSender setPath(Path path) {
		this.path = path;
		return this;
	}

	/**
	 * Request setter.
	 * 
	 * @param request
	 * @return
	 */
	public MultipartFileSender with(HttpServletRequest request) {
		this.request = request;
		return this;
	}

	/**
	 * Response setter.
	 * 
	 * @param response
	 * @return
	 */
	public MultipartFileSender with(HttpServletResponse response) {
		this.response = response;
		return this;
	}

	public void serveResource() throws IOException {

		// If not with request and response do nothing.
		if (null == request || null == response) {
			return;
		}

		// If file dosen't exist set resource not found error to response.
		if (!Files.exists(path)) {
			logger.error("File not found in path {}", path.toAbsolutePath().toString());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Get file properties.
		String extension = FilenameUtils.getExtension(path.toFile().getName());
		Long length = Files.size(path);
		String fileName = path.getFileName().toString();
		FileTime lastModifiedObj = Files.getLastModifiedTime(path);

		if (null == lastModifiedObj || StringUtils.isEmpty(fileName)) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		long lastModified = LocalDateTime
				.ofInstant(lastModifiedObj.toInstant(), ZoneId.of(ZoneOffset.systemDefault().getId()))
				.toEpochSecond(ZoneOffset.UTC);

		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentType = fileNameMap.getContentTypeFor(fileName);

		if (extension.equalsIgnoreCase("mp4")) {
			contentType = "video/mp4";
		} else if (extension.equalsIgnoreCase("mp3")) {
			contentType = "audio/mpeg";
		}

		// Validate request headers for caching
		// -------------------------------------

		// If-None-Match header then response should contain "*" or "ETag". If so then
		// return 304.
		String ifNoneMatch = request.getHeader("If-None-Match");
		if (null != ifNoneMatch && HttpUtils.matches(ifNoneMatch, fileName)) {
			response.setHeader("ETag", fileName); // Required in 304.
			response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		// If-Modified-Since header is present then should be greater than last
		// modified.
		// If so return 304.
		// This is ignored id None-Match header is present.
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if (null == ifNoneMatch && -1 != ifModifiedSince && ifModifiedSince + 1000 > lastModified) {
			response.setHeader("ETag", fileName);
			response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		// Validate request headers for resume
		// ----------------------------------------------------

		// If-Match header should contain "*" or ETag. If not, then return 412.
		String ifMatch = request.getHeader("If-Match");
		if (ifMatch != null && !HttpUtils.matches(ifMatch, fileName)) {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			return;
		}

		// If-Unmodified-Since header should be greater than LastModified. If
		// not, then return 412.
		long ifUnmodifiedSince = request.getDateHeader(HttpHeaders.IF_UNMODIFIED_SINCE);
		if (ifUnmodifiedSince != -1 && ifUnmodifiedSince + 1000 <= lastModified) {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			return;
		}

		// Validate and process range
		// -------------------------------------------------------------

		// Full range represents the complete file.
		Range full = new Range(0, length - 1, length);
		List<Range> ranges = new ArrayList<>();

		// Validate and prosess range if range header is present.
		String range = request.getHeader(HttpHeaders.RANGE);
		if (null != range) {

			// Range header should match format "bytes=n-n,n-n,n-n...". If not,
			// then return 416.
			if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
				response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes */" + length); // Required in 416.
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}

			String ifRange = request.getHeader(HttpHeaders.IF_RANGE);
			if (null != ifMatch && !ifRange.matches(fileName)) {
				try {
					long ifRangeTime = request.getDateHeader(HttpHeaders.IF_RANGE); // Throws IAE if invalid.
					if (ifRangeTime != -1) {
						ranges.add(full);
					}
				} catch (IllegalArgumentException ignore) {
					ranges.add(full);
				}
			}

			// If any valid If-Range header, then process each part of byte
			// range.
			if (ranges.isEmpty()) {
				for (String part : range.substring(6).split(",")) {
					// Assuming a file with length of 100, the following
					// examples returns bytes at:
					// 50-80 (50 to 80), 40- (40 to length=100), -20
					// (length-20=80 to length=100).
					long start = Range.sublong(part, 0, part.indexOf("-"));
					long end = Range.sublong(part, part.indexOf("-") + 1, part.length());

					if (start == -1) {
						start = length - end;
						end = length - 1;
					} else if (end == -1 || end > length - 1) {
						end = length - 1;
					}

					// Check if Range is syntactically valid. If not, then
					// return 416.
					if (start > end) {
						response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes */" + length); // Required
						// in
						// 416.
						response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
						return;
					}

					// Add range.
					ranges.add(new Range(start, end, length));
				}
			}
		}

		// Prepare and initialize response
		// --------------------------------------------------------

		// Get content type by file name and set content disposition.
		String disposition = "inline";

		if (!contentType.startsWith("image")) {
			// Else, expect for images, determine content disposition. If
			// content type is supported by
			// the browser, then set to inline, else attachment which will pop a
			// 'save as' dialogue.
			String accept = request.getHeader(HttpHeaders.ACCEPT);
			disposition = accept != null && HttpUtils.accepts(accept, contentType) ? "inline" : "attachment";
		}
		logger.debug("Content-Type : {}", contentType);
		// Initialize response.
		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, disposition + ";filename=\"" + fileName + "\"");
		logger.debug("Content-Disposition : {}", disposition);
		response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
		response.setHeader(HttpHeaders.ETAG, fileName);
		response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);
		response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis() + DEFAULT_EXPIRE_TIME);

		// Send requested file (part(s)) to client
		// ------------------------------------------------

		// Prepare streams.
		try (InputStream input = new BufferedInputStream(Files.newInputStream(path));
				OutputStream output = response.getOutputStream()) {

			if (ranges.isEmpty() || ranges.get(0) == full) {

				// Return full file.
				logger.info("Return full file");
				response.setContentType(contentType);
				response.setHeader(HttpHeaders.CONTENT_RANGE,
						"bytes " + full.start + "-" + full.end + "/" + full.total);
				response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(full.length));
				Range.copy(input, output, length, full.start, full.length);

			} else if (ranges.size() == 1) {

				// Return single part of file.
				Range r = ranges.get(0);
				logger.info("Return 1 part of file : from ({}) to ({})", r.start, r.end);
				response.setContentType(contentType);
				response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + r.start + "-" + r.end + "/" + r.total);
				response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(r.length));
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

				// Copy single part range.
				Range.copy(input, output, length, r.start, r.length);

			} else {

				// Return multiple parts of file.
				response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

				// Cast back to ServletOutputStream to get the easy println
				// methods.
				@SuppressWarnings("resource")
				ServletOutputStream sos = (ServletOutputStream) output;

				// Copy multi part range.
				for (Range r : ranges) {
					logger.info("Return multi part of file : from ({}) to ({})", r.start, r.end);
					// Add multipart boundary and header fields for every range.
					sos.println();
					sos.println("--" + MULTIPART_BOUNDARY);
					sos.println("Content-Type: " + contentType);
					sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);

					// Copy single part range of multi part range.
					Range.copy(input, output, length, r.start, r.length);
				}

				// End with multipart boundary.
				sos.println();
				sos.println("--" + MULTIPART_BOUNDARY + "--");
			}
		}
	}

	private static class Range {
		long start;
		long end;
		long length;
		long total;

		/**
		 * Construct a byte range.
		 * 
		 * @param start Start of the byte range.
		 * @param end   End of the byte range.
		 * @param total Total length of the byte source.
		 */
		public Range(long start, long end, long total) {
			this.start = start;
			this.end = end;
			this.length = end - start + 1;
			this.total = total;
		}

		public static long sublong(String value, int beginIndex, int endIndex) {
			String substring = value.substring(beginIndex, endIndex);
			return (substring.length() > 0) ? Long.parseLong(substring) : -1;
		}

		private static void copy(InputStream input, OutputStream output, long inputSize, long start, long length)
				throws IOException {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int read;

			if (inputSize == length) {
				// Write full range.
				while ((read = input.read(buffer)) > 0) {
					output.write(buffer, 0, read);
					output.flush();
				}
			} else {
				input.skip(start);
				long toRead = length;

				while ((read = input.read(buffer)) > 0) {
					if ((toRead -= read) > 0) {
						output.write(buffer, 0, read);
						output.flush();
					} else {
						output.write(buffer, 0, (int) toRead + read);
						output.flush();
						break;
					}
				}
			}
		}
	}

	private static class HttpUtils {

		/**
		 * Returns true if the given accept header accepts the given value.
		 * 
		 * @param acceptHeader The accept header.
		 * @param toAccept     The value to be accepted.
		 * @return True if the given accept header accepts the given value.
		 */
		public static boolean accepts(String acceptHeader, String toAccept) {
			String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
			Arrays.sort(acceptValues);

			return Arrays.binarySearch(acceptValues, toAccept) > -1
					|| Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "/*")) > -1
					|| Arrays.binarySearch(acceptValues, "*/*") > -1;
		}

		/**
		 * Returns true if the given match header matches the given value.
		 * 
		 * @param matchHeader The match header.
		 * @param toMatch     The value to be matched.
		 * @return True if the given match header matches the given value.
		 */
		public static boolean matches(String matchHeader, String toMatch) {
			String[] matchValues = matchHeader.split("\\s*,\\s*");
			Arrays.sort(matchValues);
			return Arrays.binarySearch(matchValues, toMatch) > -1 || Arrays.binarySearch(matchValues, "*") > -1;
		}
	}

}
