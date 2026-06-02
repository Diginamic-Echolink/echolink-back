package fr.diginamic.echolink.domain.section.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNonFoundException;

public class SectionNotFoundException extends AbstractNonFoundException {
    public SectionNotFoundException(String message) {
        super(message);
    }
}
