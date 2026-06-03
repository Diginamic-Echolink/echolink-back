package fr.diginamic.echolink.infrastructure.location.out.persistence.repository;

import fr.diginamic.echolink.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationJdbcRepository extends JpaRepository<Location, UUID> {

    @Query("SELECT l.inseeCode FROM Location l")
    Set<String> findAllInseeCodes();

    @Query("SELECT l FROM Location l WHERE l.latitude BETWEEN :latitudeMin AND :latitudeMax AND l.longitude BETWEEN :longitudeMin AND :longitudeMax")
    List<Location> findLocationsByCordonneeBetween(
            float latitudeMin,
            float latitudeMax,
            float longitudeMin,
            float longitudeMax
    );
}
