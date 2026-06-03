package fr.diginamic.echolink.domain.location.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

public class LocationNotFoundException extends AbstractNotFoundException {

    public LocationNotFoundException(String message) {
        super(message);
    }
}
