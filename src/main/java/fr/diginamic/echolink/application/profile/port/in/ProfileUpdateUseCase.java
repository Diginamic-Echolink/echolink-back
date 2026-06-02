package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.UUID;

public interface ProfileUpdateUseCase {

    Profile update(UUID id, ProfileUpdateRequest profile) throws ProfileNotFoundException;
}
