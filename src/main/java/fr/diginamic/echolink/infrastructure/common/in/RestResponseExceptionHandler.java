package fr.diginamic.echolink.infrastructure.common.in;

import fr.diginamic.echolink.domain.shared.exception.AbstractBadRequestException;
import fr.diginamic.echolink.domain.shared.exception.AbstractForbiddenException;
import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import fr.diginamic.echolink.infrastructure.common.in.dto.FieldErrorQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Global exception handler for REST controllers.
 * <p>
 * Converts application and validation exceptions into
 * standardized HTTP error responses.
 */
@ControllerAdvice
public class RestResponseExceptionHandler {

    /**
     * Handles business exceptions related to invalid requests.
     *
     * @param exception bad request exception
     * @return HTTP 400 response containing the error message
     */
    @ExceptionHandler(AbstractBadRequestException.class)
    public ResponseEntity<ErrorMessageQuery> handleBadRequestException(AbstractBadRequestException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

    /**
     * Handles forbidden access exceptions thrown when a user attempts
     * to access or modify a resource without sufficient permissions.
     *
     * @param exception forbidden exception containing the error details
     * @return HTTP 403 response containing the error message
     */
    @ExceptionHandler(AbstractForbiddenException.class)
    public ResponseEntity<ErrorMessageQuery> handleForbiddenException(AbstractForbiddenException exception) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

    /**
     * Handles resource not found exceptions.
     *
     * @param exception not found exception
     * @return HTTP 404 response containing the error message
     */
    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity<ErrorMessageQuery> handleNotFoundException(AbstractNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

    /**
     * Handles malformed or unreadable JSON request bodies.
     *
     * @param ex JSON parsing exception
     * @return HTTP 400 response indicating an invalid request format
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageQuery> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery("Invalid request format", null));
    }

    /**
     * Handles bean validation errors triggered by invalid request data.
     *
     * @param ex validation exception
     * @return HTTP 400 response containing validation details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageQuery> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldErrorQuery> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(f -> new FieldErrorQuery(f.getField(), f.getDefaultMessage()))
                .toList();

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery("Validation failed", errors));
    }
}
