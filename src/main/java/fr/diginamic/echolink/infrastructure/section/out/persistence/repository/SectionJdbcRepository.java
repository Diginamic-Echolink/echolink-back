package fr.diginamic.echolink.infrastructure.section.out.persistence.repository;

import fr.diginamic.echolink.domain.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectionJdbcRepository extends JpaRepository<Section, UUID> {}
