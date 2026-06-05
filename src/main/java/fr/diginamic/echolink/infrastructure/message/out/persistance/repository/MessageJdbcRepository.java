package fr.diginamic.echolink.infrastructure.message.out.persistance.repository;

import fr.diginamic.echolink.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageJdbcRepository extends JpaRepository<Message, UUID> {

    List<Message> findByThreadId(UUID threadId);

    Optional<Message> findById(UUID id);
}
