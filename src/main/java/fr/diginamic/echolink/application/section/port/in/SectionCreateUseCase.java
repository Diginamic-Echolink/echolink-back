package fr.diginamic.echolink.application.section.port.in;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;

public interface SectionCreateUseCase {

    Section create(SectionUpsertRequest request);
}
