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

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(AbstractBadRequestException.class)
    public ResponseEntity<ErrorMessageQuery> handleBadRequestException(AbstractBadRequestException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

    @ExceptionHandler(AbstractForbiddenException.class)
    public ResponseEntity<ErrorMessageQuery> handleForbiddenException(AbstractForbiddenException exception) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity<ErrorMessageQuery> handleNotFoundException(AbstractNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageQuery> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery("Invalid request format", null));
    }

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
