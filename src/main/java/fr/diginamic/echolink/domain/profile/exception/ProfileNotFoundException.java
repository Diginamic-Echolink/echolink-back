package fr.diginamic.echolink.domain.profile.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNonFoundException;

public class ProfileNotFoundException extends AbstractNonFoundException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
