package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;

import java.util.UUID;

public interface SectionUpdateUseCase {

    Section update(UUID id, SectionUpsertRequest request) throws SectionNotFoundException;
}
