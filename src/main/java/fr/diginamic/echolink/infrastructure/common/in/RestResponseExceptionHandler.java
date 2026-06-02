package fr.diginamic.echolink.infrastructure.common.in;

import fr.diginamic.echolink.domain.profile.exception.InvalidCredentialsException;
import fr.diginamic.echolink.infrastructure.common.in.dto.ErrorMessageQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessageQuery> handleNotFoundException(InvalidCredentialsException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessageQuery(exception.getMessage(), null));
    }

}
