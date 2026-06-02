package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;

import java.util.List;
import java.util.UUID;

public interface SectionGetUseCase {

    Section getById(UUID id) throws SectionNotFoundException;

    List<Section> getAllSections();
}
