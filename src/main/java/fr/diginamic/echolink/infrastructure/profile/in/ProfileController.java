package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileDeleteUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.profile.port.in.ProfileUpdateUseCase;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.ProfileUpdateRequest;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import fr.diginamic.echolink.infrastructure.profile.in.mapper.ProfileQueryMapper;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final ProfileGetUseCase getUseCase;
    private final ProfileUpdateUseCase updateUseCase;
    private final ProfileDeleteUseCase deleteUseCase;
    private final ProfileQueryMapper mapper;

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

    @GetMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> getProfileById(@PathVariable UUID profileId) throws ProfileNotFoundException {
        Profile profile = getUseCase.getById(profileId);
        ProfileQuery query = mapper.toQuery(profile);
        return ResponseEntity.ok(query);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<ProfileQuery>> getAllProfiles() {
        List<Profile> profiles = getUseCase.getAllProfiles();
        List<ProfileQuery> query = profiles.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    @PutMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> updateProfile(
            @PathVariable UUID profileId,
            @RequestBody ProfileUpdateRequest request
    ) throws ProfileNotFoundException {
        Profile profile = updateUseCase.update(profileId, request);
        ProfileQuery query = mapper.toQuery(profile);
        return ResponseEntity.ok(query);
    }

    @DeleteMapping("/{profileId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteProfile(@PathVariable UUID profileId) throws ProfileNotFoundException {
        deleteUseCase.delete(profileId);
        return ResponseEntity.ok().build();
    }
}
