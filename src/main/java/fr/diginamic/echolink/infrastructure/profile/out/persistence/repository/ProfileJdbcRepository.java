package fr.diginamic.echolink.infrastructure.profile.out.persistence.repository;

import fr.diginamic.echolink.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileJdbcRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByEmail(String email);
}
