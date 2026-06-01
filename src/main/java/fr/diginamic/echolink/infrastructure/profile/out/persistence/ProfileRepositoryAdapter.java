package fr.diginamic.echolink.infrastructure.profile.out.persistence;

import fr.diginamic.echolink.application.profile.port.out.ProfileRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.infrastructure.profile.out.persistence.repository.ProfileJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepository {

    private final ProfileJdbcRepository repository;

    @Override
    public Optional<Profile> getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Profile> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<Profile> getAll() {
        return repository.findAll();
    }

    @Override
    public Profile create(Profile profile) {
        return repository.save(profile);
    }

    @Override
    public void update(Profile profile) {
        repository.save(profile);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
