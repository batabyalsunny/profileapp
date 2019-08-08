/**
 * 
 */
package ml.bootcode.profileapp.dto;

import org.springframework.http.HttpStatus;

/**
 * @author sunnyb
 *
 */
public class SuccessResponseDTO<T> {

	private int status;
	private String message;
	private T data;

	/**
	 * @param data
	 */
	public SuccessResponseDTO(T data) {
		status = HttpStatus.OK.value();
		message = "Success";
		this.data = data;
	}

	/**
	 * @param message
	 * @param data
	 */
	public SuccessResponseDTO(String message, T data) {
		status = HttpStatus.OK.value();
		this.message = message;
		this.data = data;
	}

	/**
	 * @param status
	 * @param message
	 * @param data
	 */
	public SuccessResponseDTO(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
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

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
}
