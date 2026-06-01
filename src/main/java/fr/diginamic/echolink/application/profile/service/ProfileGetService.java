package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileGetService implements ProfileGetUseCase {

    private final ProfileRepository repository;

    @Override
    public Profile getById(UUID id) {
        return repository.getById(id).orElse(null);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return repository.getAll();
    }
}
