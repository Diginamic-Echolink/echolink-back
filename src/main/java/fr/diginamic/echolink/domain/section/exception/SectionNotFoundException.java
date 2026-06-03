package fr.diginamic.echolink.domain.section.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

public class SectionNotFoundException extends AbstractNotFoundException {

    public SectionNotFoundException(String message) {
        super(message);
    }
}
