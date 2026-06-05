package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for updating a profile.
 */
public interface ProfileUpdateUseCase {

    /**
     * Updates the profile identified by the specified identifier.
     *
     * @param id unique identifier of the profile to update
     * @param profile request containing the updated profile information
     * @return the updated profile
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    Profile update(UUID id, ProfileUpdateRequest profile) throws ProfileNotFoundException;
}
