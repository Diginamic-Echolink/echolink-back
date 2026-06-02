package fr.diginamic.echolink.application.profile.port.out;

import fr.diginamic.echolink.domain.profile.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository {

    Optional<Profile> getById(UUID id);

    Optional<Profile> getByEmail(String email);

    List<Profile> getAll();

    Profile save(Profile profile);

    void delete(UUID id);
}
