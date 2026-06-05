package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileDeleteUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileUpdateUseCase;
import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
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
    private final ProfileRepository repository;

    /**
     * Encoder used to hash passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a profile by its unique identifier.
     *
     * @param id unique identifier of the profile
     * @return the matching profile
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @Override
    public Profile getById(UUID id) throws ProfileNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile with id " + id + " not found"));
    }

    /**
     * Retrieves all available profiles.
     *
     * @return list of all profiles
     */
    @Override
    public List<Profile> getAllProfiles() {
        return repository.getAllProfiles();
    }

    /**
     * Updates an existing profile.
     *
     * @param id unique identifier of the profile to update
     * @param request request containing updated profile information
     * @return the updated profile
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @Override
    public Profile update(UUID id, ProfileUpdateRequest request) throws ProfileNotFoundException {
        Profile profile = getById(id);
        profile.setFirstName(request.firstName());
        profile.setLastName(request.lastName());
        profile.setPseudo(request.pseudo());
        profile.setEmail(request.email());
        profile.setPassword(passwordEncoder.encode(request.password()));
        profile.setCity(request.city());
        profile.setPostalCode(request.postalCode());
        profile.setAddress(request.address());
        profile.setPhoneNumber(request.phoneNumber());
        profile.setLinkImgProfile(request.linkImgProfile());
        return repository.save(profile);
    }

    /**
     * Deletes a profile.
     *
     * @param id unique identifier of the profile to delete
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @Override
    public void delete(UUID id) throws ProfileNotFoundException {
        getById(id);
        repository.delete(id);
    }
}
