package fr.diginamic.echolink.infrastructure.profile.out.persistence.repository;

import fr.diginamic.echolink.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileJdbcRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByEmail(String email);
}
