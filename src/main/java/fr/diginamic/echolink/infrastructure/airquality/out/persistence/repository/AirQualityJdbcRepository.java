package fr.diginamic.echolink.infrastructure.airquality.out.persistence.repository;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirQualityJdbcRepository extends JpaRepository<AirQuality, UUID> {

    @Query("SELECT a FROM AirQuality a WHERE a.location.id = :locationId ORDER BY a.recordedAt DESC LIMIT 1")
    Optional<AirQuality> findByLocationId(@Param("locationId") UUID locationId);

    @Query("SELECT a FROM AirQuality a WHERE a.location.id = :locationId ORDER BY a.recordedAt DESC LIMIT :limit")
    List<AirQuality> findAllByLocationId(@Param("locationId") UUID locationId, @Param("limit") int limit);
}
