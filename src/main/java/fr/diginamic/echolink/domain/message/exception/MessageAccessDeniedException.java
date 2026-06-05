package fr.diginamic.echolink.domain.message.exception;

public class MessageAccessDeniedException extends RuntimeException {
    public MessageAccessDeniedException(String message) {
        super(message);
    }
}