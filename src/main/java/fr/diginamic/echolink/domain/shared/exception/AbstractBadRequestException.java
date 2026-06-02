package fr.diginamic.echolink.domain.shared.exception;

public class AbstractBadRequestException extends Exception {
    public AbstractBadRequestException(String message) {
        super(message);
    }
}
