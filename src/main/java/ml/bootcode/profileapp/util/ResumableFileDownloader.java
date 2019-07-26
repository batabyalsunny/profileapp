/**
 * 
 */
package ml.bootcode.profileapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

/**
 * @author sunnyb
 *
 */
public class ResumableFileDownloader {

	private Path path;
	private String saveAsFileName;
	private HttpServletResponse response;

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
	 */
	public void download() throws IOException {

		// Get the file path object.
		File file = path.toFile();

		// If file not exists.
		if (!file.exists()) {
			throw new FileNotFoundException("File not found in the requested location.");
		}

		// If save as file name is not specified then create a random filename.
		if (null == saveAsFileName) {
			saveAsFileName = System.currentTimeMillis() + "_" + path.getFileName();
		}

		// Set response content type and content disposition.
		response.setContentType(Files.probeContentType(path));
		response.addHeader("Content-Disposition", "attachment; filename=" + saveAsFileName);

		long bytesDownloaded = 0;
		try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

			byte[] buffer = new byte[1024];

			int bytesCount;
			while ((bytesCount = is.read(buffer)) > 0) {
				os.write(buffer, 0, bytesCount);
				bytesDownloaded += bytesCount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
