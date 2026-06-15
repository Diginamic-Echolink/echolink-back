package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.application.profile.port.in.ProfileDeleteUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileUpdateUseCase;
import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.domain.location.exception.LocationNotFoundException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service responsible for profile retrieval, update and deletion operations.
 */
@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileGetUseCase, ProfileUpdateUseCase, ProfileDeleteUseCase {

    /**
     * Repository used to access profile data.
     */
    private final ProfileRepository profileRepository;

    /**
     * Repository used to access location data.
     */
    private final LocationRepository locationRepository;

    /**
     * Repository used to access message data.
     */
    private final MessageRepository messageRepository;

    /**
     * Repository used to access thread data.
     */
    private final ThreadRepository threadRepository;

    /**
     * Encoder used to hash passwords.
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public Profile getById(UUID id) throws ProfileNotFoundException {
        return profileRepository.getById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile with id " + id + " not found"));
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.getAllProfiles();
    }

    @Override
    public Profile update(Profile user, UUID id, ProfileUpdateRequest request)
            throws ProfileNotFoundException, ProfileNotAllowedException {

        if (!user.isAdmin() && !user.getId().equals(id)) {
            throw new ProfileNotAllowedException("You are not allowed to modify this profile");
        }

        Profile profile = getById(id);
        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setPseudo(request.pseudo());
        if (request.email() != null) {
            profile.setEmail(request.email());
        }
        if (request.password() != null && !request.password().isEmpty()) {
            profile.setPassword(passwordEncoder.encode(request.password()));
        }
        profile.setPostalCode(request.postalCode());
        profile.setPhoneNumber(request.phoneNumber());
        profile.setLinkImgProfile(request.linkImgProfile());
        return profileRepository.save(profile);
    }

    @Override
    public Profile addFavoriteLocation(Profile user, UUID profileId, UUID locationId)
            throws ProfileNotFoundException, ProfileNotAllowedException, LocationNotFoundException {

        if (!user.isAdmin() && !user.getId().equals(profileId)) {
            throw new ProfileNotAllowedException("You are not allowed to modify this profile");
        }

        Profile profile = getById(profileId);

        Location location = locationRepository.getById(locationId)
                .orElseThrow(() -> new LocationNotFoundException("Location with id " + locationId + " not found"));

        if (profile.getFavoriteLocations().size() >= 3) {
            throw new ProfileNotAllowedException("Maximum 3 favorite locations allowed");
        }

        profile.getFavoriteLocations().add(location);

        return profileRepository.save(profile);
    }

    @Override
    public Profile removeFavoriteLocation(Profile user, UUID profileId, UUID locationId)
            throws ProfileNotFoundException, ProfileNotAllowedException {

        if (!user.isAdmin() && !user.getId().equals(profileId)) {
            throw new ProfileNotAllowedException("You are not allowed to modify this profile");
        }

        Profile profile = getById(profileId);
        profile.getFavoriteLocations().removeIf(l -> locationId.equals(l.getId()));

        return profileRepository.save(profile);
    }

    @Override
    public void delete(Profile user, UUID id) throws ProfileNotFoundException, ProfileNotAllowedException {
        if (!user.isAdmin() && !user.getId().equals(id)) {
            throw new ProfileNotAllowedException("You are not allowed to modify this profile");
        }

        Profile profile = getById(id);
        messageRepository.removeProfileReferences(profile.getId());
        threadRepository.removeProfileReferences(profile.getId());
        profileRepository.delete(profile.getId());
    }
}
