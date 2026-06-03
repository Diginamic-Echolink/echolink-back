package fr.diginamic.echolink.domain.thread.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

public class ThreadNotFoundException extends AbstractNotFoundException {

    public ThreadNotFoundException(String message) {
        super(message);
    }
}
