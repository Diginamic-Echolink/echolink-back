package fr.diginamic.echolink.application.profile.port.out;

import fr.diginamic.echolink.domain.profile.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines the contract for accessing and persisting profile data.
 */
public interface ProfileRepository {

    /**
     * Retrieves a profile by its unique identifier.
     *
     * @param id unique identifier of the profile
     * @return an {@link Optional} containing the profile if found
     */
    Optional<Profile> getById(UUID id);

    /**
     * Retrieves a profile by its email address.
     *
     * @param email email address of the profile
     * @return an {@link Optional} containing the profile if found
     */
    Optional<Profile> getByEmail(String email);

    /**
     * Retrieves all available profiles.
     *
     * @return list of all profiles
     */
    List<Profile> getAllProfiles();

    /**
     * Persists a profile.
     *
     * @param profile profile to save
     * @return the saved profile
     */
    Profile save(Profile profile);

    /**
     * Deletes the profile identified by the specified identifier.
     *
     * @param id unique identifier of the profile to delete
     */
    void delete(UUID id);
}
