package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for deleting a section.
 */
public interface SectionDeleteUseCase {

    /**
     * Deletes the section identified by the specified identifier.
     *
     * @param id unique identifier of the section to delete
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    void delete(UUID id) throws SectionNotFoundException;
}
