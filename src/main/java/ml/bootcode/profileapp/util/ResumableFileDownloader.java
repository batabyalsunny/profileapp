/**
 * 
 */
package ml.bootcode.profileapp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

/**
 * @author sunnyb
 *
 */
public class ResumableFileDownloader {

	private Path path;
	private String saveAsFileName;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private static final int BUFFER_SIZE = 1024;

	/**
	 * Download from Path object.
	 * 
	 * @param path
	 * @return
	 */
	public static ResumableFileDownloader fromPath(Path path) {
		return new ResumableFileDownloader().setPath(path);
	}

	/**
	 * Download from system path.
	 * 
	 * @param path
	 * @return
	 */
	public static ResumableFileDownloader fromPath(String path) {
		return new ResumableFileDownloader().setPath(Paths.get(path));
	}

	/**
	 * From URI.
	 * 
	 * @param uri
	 * @return
	 */
	public static ResumableFileDownloader fromUri(String uri) {
		return new ResumableFileDownloader().setPath(Paths.get(uri));
	}

	/**
	 * From file object.
	 * 
	 * @param file
	 * @return
	 */
	public static ResumableFileDownloader fromFile(File file) {
		return new ResumableFileDownloader().setPath(file.toPath());
	}

	/**
	 * Internal setter for file path.
	 * 
	 * @param path
	 * @return
	 */
	private ResumableFileDownloader setPath(Path path) {
		this.path = path;
		return this;
	}

	/**
	 * Setter for request.
	 * 
	 * @param request
	 * @return
	 */
	public ResumableFileDownloader with(HttpServletRequest request) {
		this.request = request;
		return this;
	}

	/**
	 * Setter for response.
	 * 
	 * @param response
	 * @return
	 */
	public ResumableFileDownloader with(HttpServletResponse response) {
		this.response = response;
		return this;
	}

	/**
	 * Setter for download as filename.
	 * 
	 * @param fileName
	 * @return
	 */
	public ResumableFileDownloader as(String fileName) {
		saveAsFileName = fileName;
		return this;
	}

	/**
	 * Download.
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public void download() throws IOException, NoSuchAlgorithmException {

		// If file not exists.
		if (!Files.exists(path)) {
			throw new FileNotFoundException("File not found in the requested location.");
		}

		List<Range> ranges = new ArrayList<>();

		// Get file last modified date.
		FileTime resourceLastModifiedTime = Files.getLastModifiedTime(path);

		// Get content type.
		String contentType = Files.probeContentType(path);

		// Get file length.
		long length = Files.size(path);

		// If unable to find last modified time.
		if (null == resourceLastModifiedTime || StringUtils.isEmpty(resourceLastModifiedTime)) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		// Convert last modified time to UTC.
		long lastModified = LocalDateTime
				.ofInstant(resourceLastModifiedTime.toInstant(), ZoneId.of(ZoneOffset.systemDefault().getId()))
				.toEpochSecond(ZoneOffset.UTC);

		// If save as file name is not specified then create a random filename.
		if (null == saveAsFileName) {
			saveAsFileName = System.currentTimeMillis() + "_" + path.getFileName();
		}

		// Prepare etag value by hashing the filename.
		String eTag = new BigInteger(1,
				MessageDigest.getInstance("SHA-256").digest(path.getFileName().toString().getBytes())).toString();

		// -------------- Verify headers for resume --------------//

		// Validate ifmatch header.
		String ifMatch = request.getHeader(HttpHeaders.IF_MATCH);

		// If ifMatch header doesn't match with the Etag value.
		if (eTag.equals(ifMatch)) {
			// Return 412.
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			return;
		}

		// Validate the last modified date.
		long ifUnmodifiedSince = request.getDateHeader(HttpHeaders.IF_UNMODIFIED_SINCE);
		if (-1 != ifUnmodifiedSince && lastModified > ifUnmodifiedSince) {
			// Return 412.
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			return;
		}

		// Validate range header.
		String range = request.getHeader(HttpHeaders.RANGE);
		if (null != range) {

			// Validate if the range start is proper or not.
			if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
				// Send unsatisfiable range 416.
				response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes */" + length);
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}

			// For multiple ranges.
			for (String subRange : range.substring(6).split(",")) {

				// Get start and end from the range.
				long start = Long.parseLong(subRange.split("-")[0]);
				long end = Long.parseLong(subRange.split("-")[1]);

				if (start >= end) {
					// Send unsatisfiable range 416.
					response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes */" + length);
					response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
					return;
				}

				ranges.add(new Range(start, end));
			}
		}

		// -------------- Set the common headers ----------------//

		response.reset();
		response.setBufferSize(BUFFER_SIZE);

		// Set response content type and content disposition.
		response.setContentType(contentType);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + saveAsFileName);

		// For 206.
		response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
		response.setHeader(HttpHeaders.ETAG, eTag);
		response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);

		// -------------- Send the actual resource ----------------//

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path));
				OutputStream os = response.getOutputStream()) {

			// If no range is present to be sent.
			if (ranges.isEmpty()) {

				// Set required range headers.
				response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes 0-" + (length - 1) + "/" + length);
				response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));

				// Send the full Content using byte stream.
				Range.send(is, os, 0, length - 1);
			} else if (1 == ranges.size()) { // For single range.

				Range r = ranges.get(0);

				// Set required range headers.
				response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + r.start + "-" + r.end + "/" + length);
				response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(r.end - r.start));
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

				// Send the only one requested range.
				Range.send(is, os, r.start, r.end);
			} else { // for multiple requested ranges.

				for (Range r : ranges) {
					// Send content for all the ranges.
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class Range {
		long start;
		long end;

		Range(long start, long end) {
			this.start = start;
			this.end = end;
		}

		private static void send(InputStream input, OutputStream output, long start, long end) throws IOException {

			byte[] buffer = new byte[BUFFER_SIZE];

			if (0 < start) {
				input.skip(start - 1);
			}

			int bytesCount;
			while ((bytesCount = input.read(buffer)) > 0) {
				output.write(buffer, 0, bytesCount);
				output.flush();
			}
		}
	}
}
