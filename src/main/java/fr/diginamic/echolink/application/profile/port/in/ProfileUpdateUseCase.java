package fr.diginamic.echolink.application.profile.port.in;

import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;

import java.util.UUID;

/**
 * Defines the use case for updating a profile.
 */
public interface ProfileUpdateUseCase {

    /**
     * Updates the profile identified by the specified identifier.
     *
     * @param user profile that made the request
     * @param id unique identifier of the profile to update
     * @param profile request containing the updated profile information
     * @return the updated profile
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    Profile update(Profile user, UUID id, ProfileUpdateRequest profile)
            throws ProfileNotFoundException, ProfileNotAllowedException;

    /**
     * Adds a location to the favorite locations of a profile.
     * <p>
     * A profile can have a maximum of 5 favorite locations.
     * Only the profile owner or an administrator can perform this operation.
     * Attempting to add a duplicate location has no effect or may be ignored depending on implementation.
     * </p>
     *
     * @param user the authenticated profile performing the request
     * @param profileId unique identifier of the profile to update
     * @param locationId unique identifier of the location to add to favorites
     * @return the updated profile containing the new favorite location
     * @throws ProfileNotFoundException if the profile does not exist
     * @throws LocationNotFoundException if the location does not exist
     * @throws ProfileNotAllowedException if the user is not allowed to modify this profile
     */
    Profile addFavoriteLocation(Profile user, UUID profileId, UUID locationId)
            throws ProfileNotFoundException, ProfileNotAllowedException, LocationNotFoundException;

    /**
     * Removes a location from the favorite locations of a profile.
     * <p>
     * Only the profile owner or an administrator can perform this operation.
     * If the location is not currently in the favorites list, the operation has no effect.
     * </p>
     *
     * @param user the authenticated profile performing the request
     * @param profileId unique identifier of the profile to update
     * @param locationId unique identifier of the location to remove from favorites
     * @return the updated profile without the removed favorite location
     * @throws ProfileNotFoundException if the profile does not exist
     * @throws ProfileNotAllowedException if the user is not allowed to modify this profile
     */
    Profile removeFavoriteLocation(Profile user, UUID profileId, UUID locationId)
            throws ProfileNotFoundException, ProfileNotAllowedException;
}
