package fr.diginamic.echolink.infrastructure.section.out.persistence;

import fr.diginamic.echolink.application.section.port.out.SectionRepository;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.infrastructure.section.out.persistence.repository.SectionJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing the section repository port using a JPA repository.
 */
@Component
@RequiredArgsConstructor
public class SectionRepositoryAdapter implements SectionRepository {

    /**
     * Repository used to access section persistence data.
     */
    private final SectionJdbcRepository repository;

    /**
     * Retrieves a section by its unique identifier.
     *
     * @param id unique identifier of the section
     * @return an {@link Optional} containing the section if found
     */
    @Override
    public Optional<Section> getById(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all available sections.
     *
     * @return list of all sections
     */
    @Override
    public List<Section> getAllSections() {
        return repository.findAll();
    }

    /**
     * Persists a section.
     *
     * @param section section to save
     * @return the saved section
     */
    @Override
    public Section save(Section section) {
        return repository.save(section);
    }

    /**
     * Deletes a section by its unique identifier.
     *
     * @param id unique identifier of the section to delete
     */
    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
