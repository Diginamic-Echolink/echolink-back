package fr.diginamic.echolink.infrastructure.airquality.out.persistence.repository;

import fr.diginamic.echolink.domain.airquality.AirQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for managing {@link AirQuality} entities.
 * Provides custom queries to retrieve air quality records associated with a location.
 */
public interface AirQualityJdbcRepository extends JpaRepository<AirQuality, UUID> {

    /**
     * Retrieves the most recent air quality record for a location.
     *
     * @param locationId unique identifier of the location
     * @return an optional containing the latest air quality record if found
     */
    @Query("SELECT a FROM AirQuality a WHERE a.location.id = :locationId ORDER BY a.recordedAt DESC LIMIT 1")
    Optional<AirQuality> findByLocationId(@Param("locationId") UUID locationId);

    /**
     * Retrieves a limited number of air quality records for a location,
     * ordered from the most recent to the oldest.
     *
     * @param locationId unique identifier of the location
     * @param limit maximum number of records to retrieve
     * @return a list of air quality records associated with the location
     */
    @Query("SELECT a FROM AirQuality a WHERE a.location.id = :locationId ORDER BY a.recordedAt DESC LIMIT :limit")
    List<AirQuality> findAllByLocationId(@Param("locationId") UUID locationId, @Param("limit") int limit);
}
