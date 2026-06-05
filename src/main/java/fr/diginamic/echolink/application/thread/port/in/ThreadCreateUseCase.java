package fr.diginamic.echolink.application.thread.port.in;

import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadCreateRequest;

public interface ThreadCreateUseCase {

    Thread create(ThreadCreateRequest request) throws SectionNotFoundException, ProfileNotFoundException;
}
