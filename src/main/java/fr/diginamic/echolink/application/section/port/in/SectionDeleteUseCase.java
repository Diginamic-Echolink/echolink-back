package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;

import java.util.UUID;

public interface SectionDeleteUseCase {

    void delete(UUID id) throws SectionNotFoundException;
}
