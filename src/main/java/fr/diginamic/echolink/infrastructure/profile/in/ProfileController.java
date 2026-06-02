package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.infrastructure.profile.in.dto.ProfileQuery;
import fr.diginamic.echolink.infrastructure.profile.in.mapper.ProfileQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final ProfileGetUseCase profileGetUseCase;
    private final ProfileQueryMapper profileQueryMapper;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/test")
    public ResponseEntity<?> test(Authentication authentication) {
        return ResponseEntity.ok(
                authentication.getAuthorities()
        );
    }

    @GetMapping("/me")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        if (jwt != null) {
            Profile profile = profileGetUseCase.getById(UUID.fromString(jwt.getSubject()));
            ProfileQuery query = profileQueryMapper.toQuery(profile);
            return ResponseEntity.ok(query);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<ProfileQuery> getProfileById(@PathVariable UUID profileId) {
        Profile profile = profileGetUseCase.getById(profileId);
        ProfileQuery query = profileQueryMapper.toQuery(profile);
        return ResponseEntity.ok(query);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<ProfileQuery>> getAllProfiles() {
        List<Profile> profiles = profileGetUseCase.getAllProfiles();
        List<ProfileQuery> query = profiles.stream().map(profileQueryMapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

}
