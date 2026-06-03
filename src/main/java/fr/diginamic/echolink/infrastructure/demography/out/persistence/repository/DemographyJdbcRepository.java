package fr.diginamic.echolink.infrastructure.demography.out.persistence.repository;

import fr.diginamic.echolink.domain.demography.Demography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DemographyJdbcRepository extends JpaRepository<Demography, UUID> {

    @Query("SELECT d FROM Demography d WHERE d.location.id = :locationId ORDER BY d.recordedAt DESC LIMIT 1")
    Optional<Demography> getByLocationId(@Param("locationId") UUID locationId);

    @Query("SELECT d FROM Demography d WHERE d.location.id = :locationId ORDER BY d.recordedAt DESC LIMIT :limit")
    List<Demography> getAllByLocationId(@Param("locationId") UUID locationId, @Param("limit") int limit);

}

