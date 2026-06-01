package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.Profile;

import java.util.List;
import java.util.UUID;

public interface ProfileGetUseCase {

    Profile getById(UUID id);

    List<Profile> getAllProfiles();
}
