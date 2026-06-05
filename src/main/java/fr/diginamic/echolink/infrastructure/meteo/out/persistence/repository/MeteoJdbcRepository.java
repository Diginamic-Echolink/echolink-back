package fr.diginamic.echolink.infrastructure.meteo.out.persistence.repository;

import fr.diginamic.echolink.domain.meteo.Meteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for accessing weather data.
 */
public interface MeteoJdbcRepository extends JpaRepository<Meteo, UUID> {

    /**
     * Retrieves the most recent weather record associated with a location.
     *
     * @param locationId unique identifier of the location
     * @return an {@link Optional} containing the latest weather record if found
     */
    @Query("SELECT m FROM Meteo m WHERE m.location.id = :locationId ORDER BY m.recordedAt DESC LIMIT 1")
    Optional<Meteo> findLastByLocationId(@Param("locationId") UUID locationId);

    /**
     * Retrieves a limited number of weather records associated with a location.
     *
     * @param locationId unique identifier of the location
     * @param limit maximum number of weather records to retrieve
     * @return list of weather records ordered by recording date descending
     */
    @Query("SELECT m FROM Meteo m WHERE m.location.id = :locationId ORDER BY m.recordedAt DESC LIMIT :limit")
    List<Meteo> findAllByLocationId(@Param("locationId") UUID locationId, @Param("limit") int limit);
}
