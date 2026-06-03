package fr.diginamic.echolink.infrastructure.thread.out.persistence.repository;

import fr.diginamic.echolink.domain.thread.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ThreadJdbcRepository extends JpaRepository<Thread, UUID> {

    List<Thread> findAllBySectionId(UUID sectionId);
}
