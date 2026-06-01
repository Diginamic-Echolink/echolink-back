package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileUpdateUseCase;
import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileUpdateService implements ProfileUpdateUseCase {

    private final ProfileRepository repository;

    @Override
    public void update(Profile profile) {
        repository.update(profile);
    }
}
