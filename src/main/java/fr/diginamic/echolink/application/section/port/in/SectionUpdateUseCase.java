package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for updating a section.
 */
public interface SectionUpdateUseCase {

    /**
     * Updates the section identified by the specified identifier.
     *
     * @param id unique identifier of the section to update
     * @param request request containing updated section information
     * @return the updated section
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    Section update(UUID id, SectionUpsertRequest request) throws SectionNotFoundException;
}
