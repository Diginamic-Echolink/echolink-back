package fr.diginamic.echolink.infrastructure.profile.in;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
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

    @GetMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Profile getProfile(@PathVariable UUID profileId) {
        return profileGetUseCase.getById(profileId);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<Profile> getAllProfiles() {
        return profileGetUseCase.getAllProfiles();
    }

}
