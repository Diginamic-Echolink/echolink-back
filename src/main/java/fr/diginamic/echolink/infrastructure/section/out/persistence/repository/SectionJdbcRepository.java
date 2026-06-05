package fr.diginamic.echolink.infrastructure.section.out.persistence.repository;

import fr.diginamic.echolink.domain.section.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository for accessing section data.
 */
public interface SectionJdbcRepository extends JpaRepository<Section, UUID> {}
