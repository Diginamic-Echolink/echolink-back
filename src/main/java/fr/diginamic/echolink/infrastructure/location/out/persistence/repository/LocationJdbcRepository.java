package fr.diginamic.echolink.infrastructure.location.out.persistence.repository;

import fr.diginamic.echolink.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface LocationJdbcRepository extends JpaRepository<Location, UUID> {

    @Query("SELECT l.inseeCode FROM Location l")
    Set<String> findAllInseeCodes();
}
