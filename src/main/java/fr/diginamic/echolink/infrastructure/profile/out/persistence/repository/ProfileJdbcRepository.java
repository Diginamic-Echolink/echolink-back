package fr.diginamic.echolink.infrastructure.profile.out.persistence.repository;

import fr.diginamic.echolink.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for accessing profile data.
 */
public interface ProfileJdbcRepository extends JpaRepository<Profile, UUID> {

    /**
     * Retrieves a profile by its email address.
     *
     * @param email email address of the profile
     * @return an {@link Optional} containing the profile if found
     */
    Optional<Profile> findByEmail(String email);
}
