package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileDeleteUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileUpdateUseCase;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageQuery;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import fr.diginamic.echolink.infrastructure.profile.in.mapper.ProfileQueryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing profile management endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    /**
     * Use case responsible for retrieving profiles.
     */
    private final ProfileGetUseCase getUseCase;

    /**
     * Use case responsible for updating profiles.
     */
    private final ProfileUpdateUseCase updateUseCase;

    /**
     * Use case responsible for deleting profiles.
     */
    private final ProfileDeleteUseCase deleteUseCase;

    /**
     * Mapper used to convert profile domain objects into query DTOs.
     */
    private final ProfileQueryMapper mapper;

    /**
     * Retrieves the authenticated profile.
     *
     * @param authentication current authentication information
     * @return authenticated profile information
     * @throws ProfileNotFoundException if the authenticated profile cannot be found
     */
    @GetMapping("/me")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> me(Authentication authentication) throws ProfileNotFoundException {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        if (jwt != null) {
            Profile profile = getUseCase.getById(UUID.fromString(jwt.getSubject()));
            ProfileQuery query = mapper.toQuery(profile);
            return ResponseEntity.ok(query);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves a profile by its unique identifier.
     *
     * @param profileId unique identifier of the profile
     * @return profile information
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @GetMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> getProfileById(@PathVariable UUID profileId) throws ProfileNotFoundException {
        Profile profile = getUseCase.getById(profileId);
        ProfileQuery query = mapper.toQuery(profile);
        return ResponseEntity.ok(query);
    }

    /**
     * Retrieves all available profiles.
     *
     * @return list of profile information
     */
    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<ProfileQuery>> getAllProfiles() {
        List<Profile> profiles = getUseCase.getAllProfiles();
        List<ProfileQuery> query = profiles.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    /**
     * Updates a profile.
     *
     * @param profileId unique identifier of the profile to update
     * @param request request containing updated profile information
     * @return updated profile information
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @PutMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> updateProfile(
            @PathVariable UUID profileId,
            @Valid @RequestBody ProfileUpdateRequest request
    ) throws ProfileNotFoundException {
        Profile profile = updateUseCase.update(profileId, request);
        ProfileQuery query = mapper.toQuery(profile);
        return ResponseEntity.ok(query);
    }

    /**
     * Deletes a profile.
     *
     * @param profileId unique identifier of the profile to delete
     * @return confirmation message
     * @throws ProfileNotFoundException if no profile is found with the specified identifier
     */
    @DeleteMapping("/{profileId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageQuery> deleteProfile(@PathVariable UUID profileId) throws ProfileNotFoundException {
        deleteUseCase.delete(profileId);
        return ResponseEntity.ok(new MessageQuery("Profile with id: " + profileId + " is correctly deleted"));
    }
}
