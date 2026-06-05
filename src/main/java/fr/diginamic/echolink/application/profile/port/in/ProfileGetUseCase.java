package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Defines the use cases for retrieving profiles.
 */
public interface ProfileGetUseCase {

    /**
     * Retrieves a profile by its unique identifier.
     *
     * @param id unique identifier of the profile
     * @return the matching profile
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    Profile getById(UUID id) throws ProfileNotFoundException;

    /**
     * Retrieves all available profiles.
     *
     * @return list of all profiles
     */
    List<Profile> getAllProfiles();
}
