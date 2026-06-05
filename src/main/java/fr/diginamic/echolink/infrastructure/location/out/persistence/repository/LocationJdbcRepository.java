package fr.diginamic.echolink.infrastructure.location.out.persistence.repository;

import fr.diginamic.echolink.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.UUID;

/**
 * Repository providing persistence operations for {@link Location} entities.
 */
public interface LocationJdbcRepository extends JpaRepository<Location, UUID> {

    /**
     * Searches for locations whose name contains the specified value.
     * Results are ordered by population in descending order.
     *
     * @param name location name or partial name to search for
     * @return matching locations ordered by population
     */
    @Query("""
        SELECT l
        FROM Location l
        WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))
        ORDER BY l.population DESC
    """)
    List<Location> findAllByNameContaining(@Param("name") String name);

    /**
     * Retrieves locations located within the specified geographical boundaries.
     * Results are ordered by population in descending order and limited
     * to the specified number of records.
     *
     * @param latitudeMin minimum latitude
     * @param latitudeMax maximum latitude
     * @param longitudeMin minimum longitude
     * @param longitudeMax maximum longitude
     * @param limit maximum number of locations to return
     * @return locations matching the provided coordinates range
     */
    @Query("""
        SELECT l
        FROM Location l
        WHERE l.latitude BETWEEN :latitudeMin AND :latitudeMax
        AND l.longitude BETWEEN :longitudeMin AND :longitudeMax
        ORDER BY l.population DESC
        LIMIT :limit
        """)
    List<Location> findAllByCordonneeBetween(
            @Param("latitudeMin") double latitudeMin,
            @Param("latitudeMax") double latitudeMax,
            @Param("longitudeMin") double longitudeMin,
            @Param("longitudeMax") double longitudeMax,
            @Param("limit") int limit
    );

    /**
     * Retrieves all INSEE codes stored in the database.
     *
     * @return set of INSEE codes
     */
    @Query("SELECT l.inseeCode FROM Location l")
    Set<String> findAllInseeCodes();

    /**
     * Retrieves locations for which no weather data has been synchronized
     * during the specified day.
     *
     * @param startOfDay start of the day
     * @param endOfDay end of the day
     * @return locations requiring weather synchronization
     */
    @Query("""
        SELECT l
        FROM Location l
        WHERE l.id NOT IN (
            SELECT m.location.id
            FROM Meteo m
            WHERE m.recordedAt >= :startOfDay
            AND m.recordedAt < :endOfDay
        )
    """)
    List<Location> findAllLocationsToSyncMeteoToday(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );

    /**
     * Retrieves locations for which no air quality data has been synchronized
     * during the specified day.
     *
     * @param startOfDay start of the day
     * @param endOfDay end of the day
     * @return locations requiring air quality synchronization
     */
    @Query("""
        SELECT l
        FROM Location l
        WHERE l.id NOT IN (
            SELECT a.location.id
            FROM AirQuality a
            WHERE a.recordedAt >= :startOfDay
            AND a.recordedAt < :endOfDay
        )
    """)
    List<Location> findAllLocationsToSyncAirQualityToday(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
}
