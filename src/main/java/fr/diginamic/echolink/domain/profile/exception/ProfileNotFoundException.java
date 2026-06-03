package fr.diginamic.echolink.domain.profile.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

public class ProfileNotFoundException extends AbstractNotFoundException {

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
