package fr.diginamic.echolink.domain.profile.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractBadRequestException;

public class InvalidCredentialsException extends AbstractBadRequestException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
