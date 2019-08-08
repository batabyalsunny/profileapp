/**
 * 
 */
package ml.bootcode.profileapp.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

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

		logger.debug(ex.getStackTrace().toString());

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

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

		logger.debug(ex.getMessage());

		return new ResponseEntity<>(
				new ErrorResponseDTO(HttpStatus.CONFLICT.value(), ex.getClass().getName(), ex.getMessage()),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		logger.debug(ex.getMessage());

		List<String> errors = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.add(field + "=>" + message);
		});

		return new ResponseEntity<>(
				new ErrorResponseDTO(HttpStatus.CONFLICT.value(), ex.getClass().getName(), errors.toString()),
				HttpStatus.CONFLICT);
	}
}
