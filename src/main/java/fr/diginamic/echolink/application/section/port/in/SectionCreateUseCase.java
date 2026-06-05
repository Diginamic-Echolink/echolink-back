package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;

/**
 * Defines the use case for creating a section.
 */
public interface SectionCreateUseCase {

    /**
     * Creates a new section.
     *
     * @param request request containing section information
     * @return the created section
     */
    Section create(SectionUpsertRequest request);
}
