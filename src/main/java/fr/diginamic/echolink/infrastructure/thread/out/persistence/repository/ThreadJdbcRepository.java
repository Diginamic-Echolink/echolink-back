package fr.diginamic.echolink.infrastructure.thread.out.persistence.repository;

import fr.diginamic.echolink.domain.thread.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository for accessing thread data.
 */
public interface ThreadJdbcRepository extends JpaRepository<Thread, UUID> {

    /**
     * Retrieves all threads associated with a profile.
     *
     * @param profileId unique identifier of the profile
     * @return list of threads belonging to the profile
     */
    List<Thread> findAllByProfileId(UUID profileId);

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    @Query("""
        SELECT t
        FROM Thread t
        LEFT JOIN t.messages m
        WHERE t.section.id = :sectionId
        GROUP BY t
        ORDER BY MAX(m.createdAt) DESC NULLS LAST
    """)
    List<Thread> findAllBySectionId(@Param("sectionId") UUID sectionId);

    /**
     * Retrieves paginated threads belonging to a given section.
     *
     * @param sectionId unique identifier of the section
     * @param pageable pagination and sorting information
     * @return list of threads belonging to the section
     */
    @Query("""
        SELECT t
        FROM Thread t
        LEFT JOIN t.messages m
        WHERE t.section.id = :sectionId
        GROUP BY t
        ORDER BY MAX(m.createdAt) DESC NULLS LAST
    """)
    Page<Thread> findAllBySectionId(@Param("sectionId") UUID sectionId, Pageable pageable);
}
