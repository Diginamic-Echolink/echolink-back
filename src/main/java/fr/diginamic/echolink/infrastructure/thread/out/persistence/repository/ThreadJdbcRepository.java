package fr.diginamic.echolink.infrastructure.thread.out.persistence.repository;

import fr.diginamic.echolink.domain.thread.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository for accessing thread data.
 */
public interface ThreadJdbcRepository extends JpaRepository<Thread, UUID> {

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    List<Thread> findAllBySectionId(UUID sectionId);
}
