/**
 * 
 */
package ml.bootcode.profileapp.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author sunnyb
 *
 */
public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private HttpStatus status;

	/**
	 * @param message
	 */
	public ApiException(String message) {
		this.message = message;
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	/**
	 * @param message
	 * @param status
	 */
	public ApiException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
