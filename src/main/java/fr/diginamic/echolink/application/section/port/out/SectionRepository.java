package fr.diginamic.echolink.application.section.port.out;

import fr.diginamic.echolink.domain.section.Section;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectionRepository {

    Optional<Section> getById(UUID id);

    List<Section> getAllSections();

    Section save(Section section);

    void delete(UUID id);
}
