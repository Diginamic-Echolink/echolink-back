package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.UUID;

public interface ProfileDeleteUseCase {

    void delete(UUID id) throws ProfileNotFoundException;
}
