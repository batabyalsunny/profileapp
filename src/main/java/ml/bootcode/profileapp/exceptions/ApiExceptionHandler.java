/**
 * 
 */
package ml.bootcode.profileapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import ml.bootcode.profileapp.dto.ErrorResponseDTO;

/**
 * @author sunnyb
 *
 */
@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {

		String errorMessage = ex.getMessage();
		errorMessage = (null == errorMessage) ? "Internal Server Error" : errorMessage;

		return new ResponseEntity<>(
				new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getClass().getName(), errorMessage),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponseDTO> handleNullPointerException(NullPointerException ex) {

		String errorMessage = ex.getMessage();
		errorMessage = (null == errorMessage) ? "Null Pointer Exception" : errorMessage;

		return new ResponseEntity<>(
				new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getClass().getName(), errorMessage),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponseDTO> handleApiException(ApiException ex) {
		return new ResponseEntity<>(
				new ErrorResponseDTO(ex.getStatus().value(), ex.getClass().getName(), ex.getMessage()), ex.getStatus());
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException ex) {
		return new ResponseEntity<>(new ErrorResponseDTO(ex.getStatus().value(), ex.getReason(), ex.getMessage()),
				ex.getStatus());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {
		return new ResponseEntity<>(
				new ErrorResponseDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getClass().getName(), ex.getMessage()),
				HttpStatus.METHOD_NOT_ALLOWED);
	}
}
