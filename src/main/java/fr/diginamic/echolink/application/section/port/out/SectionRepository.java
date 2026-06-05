package fr.diginamic.echolink.application.section.port.out;

import fr.diginamic.echolink.domain.section.Section;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract for accessing and persisting section data.
 */
public interface SectionRepository {

    /**
     * Retrieves a section by its unique identifier.
     *
     * @param id unique identifier of the section
     * @return an {@link Optional} containing the section if found
     */
    Optional<Section> getById(UUID id);

    /**
     * Retrieves all available sections.
     *
     * @return list of all sections
     */
    List<Section> getAllSections();

    /**
     * Persists a section.
     *
     * @param section section to save
     * @return the saved section
     */
    Section save(Section section);

    /**
     * Deletes the section identified by the specified identifier.
     *
     * @param id unique identifier of the section to delete
     */
    void delete(UUID id);
}
