package fr.diginamic.echolink.domain.profile.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractForbiddenException;

public class ProfileNotAllowedException extends AbstractForbiddenException {

    public ProfileNotAllowedException(String message) {
        super(message);
    }
}
