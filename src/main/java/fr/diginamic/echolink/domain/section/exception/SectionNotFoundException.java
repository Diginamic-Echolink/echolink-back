package fr.diginamic.echolink.domain.section.exception;

import fr.diginamic.echolink.domain.shared.exception.AbstractNotFoundException;

/**
 * Exception thrown when a requested section cannot be found.
 */
public class SectionNotFoundException extends AbstractNotFoundException {

    /**
     * Creates a new exception for a section that could not be found.
     *
     * @param message error message describing the missing section
     */
    public SectionNotFoundException(String message) {
        super(message);
    }
}
