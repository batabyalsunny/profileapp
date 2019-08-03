/**
 * 
 */
package ml.bootcode.profileapp.dto;

/**
 * @author sunnyb
 *
 */
public class ErrorResponseDTO {

	private int status;
	private String cause;
	private String message;

	/**
	 * @param status
	 * @param cause
	 * @param message
	 */
	public ErrorResponseDTO(int status, String cause, String message) {
		this.status = status;
		this.cause = cause;
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
