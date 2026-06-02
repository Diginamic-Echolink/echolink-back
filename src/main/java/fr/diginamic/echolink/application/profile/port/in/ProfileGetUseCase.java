package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ProfileGetUseCase {

    Profile getById(UUID id) throws ProfileNotFoundException;

    List<Profile> getAllProfiles();
}
