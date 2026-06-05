package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Defines the use cases for retrieving sections.
 */
public interface SectionGetUseCase {

    /**
     * Retrieves a section by its unique identifier.
     *
     * @param id unique identifier of the section
     * @return the matching section
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    Section getById(UUID id) throws SectionNotFoundException;

    /**
     * Retrieves all available sections.
     *
     * @return list of all sections
     */
    List<Section> getAllSections();
}
