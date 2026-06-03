package fr.diginamic.echolink.infrastructure.meteo.out.persistence.repository;

import fr.diginamic.echolink.domain.meteo.Meteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeteoJdbcRepository extends JpaRepository<Meteo, UUID> {

    @Query("SELECT m FROM Meteo m WHERE m.location.id = :locationId ORDER BY m.recordedAt DESC LIMIT 1")
    Optional<Meteo> getMeteoByLocationId(@Param("locationId") UUID locationId);

    @Query("SELECT m FROM Meteo m WHERE m.location.id = :locationId ORDER BY m.recordedAt DESC LIMIT :limit")
    List<Meteo> getAllMeteoByLocationId(@Param("locationId") UUID locationId, @Param("limit") int limit);

}
