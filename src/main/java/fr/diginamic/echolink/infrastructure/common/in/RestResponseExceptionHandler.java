package fr.diginamic.echolink.infrastructure.common.in;

import fr.diginamic.echolink.domain.shared.exception.AbstractBadRequestException;
import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(AbstractBadRequestException.class)
    public ResponseEntity<ErrorMessageQuery> handleBadRequestException(AbstractBadRequestException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery(exception.getMessage()));
    }

    @ExceptionHandler(AbstractNotFoundException.class)
    public ResponseEntity<ErrorMessageQuery> handleNotFoundException(AbstractNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorMessageQuery(exception.getMessage()));
    }
}
