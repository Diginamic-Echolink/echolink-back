package fr.diginamic.echolink.domain.location.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNonFoundException;

public class LocationNotFoundException extends AbstractNonFoundException {

    public LocationNotFoundException(String message) {
        super(message);
    }
}
