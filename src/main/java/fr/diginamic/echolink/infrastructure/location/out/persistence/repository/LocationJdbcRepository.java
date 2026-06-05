package fr.diginamic.echolink.infrastructure.location.out.persistence.repository;

import fr.diginamic.echolink.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.UUID;

public interface LocationJdbcRepository extends JpaRepository<Location, UUID> {

    @Query("""
        SELECT l
        FROM Location l
        WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))
        ORDER BY l.population DESC
    """)
    List<Location> findAllByNameContaining(@Param("name") String name);

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

    @Query("SELECT l.inseeCode FROM Location l")
    Set<String> findAllInseeCodes();

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
