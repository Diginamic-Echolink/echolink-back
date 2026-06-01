package fr.diginamic.echolink.application.profile.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileDeleteUseCase;
import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileDeleteService implements ProfileDeleteUseCase {

    private final ProfileRepository repository;

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }
}
