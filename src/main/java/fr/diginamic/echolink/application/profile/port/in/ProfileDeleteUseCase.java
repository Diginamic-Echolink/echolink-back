package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for deleting a profile.
 */
public interface ProfileDeleteUseCase {

    /**
     * Deletes the profile identified by the specified identifier.
     *
     * @param id unique identifier of the profile to delete
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    void delete(UUID id) throws ProfileNotFoundException;
}
