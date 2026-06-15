package fr.diginamic.echolink.infrastructure.message.out.persistance.repository;

import fr.diginamic.echolink.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link Message} persistence operations.
 * <p>
 * Provides basic CRUD operations inherited from {@link JpaRepository}
 * as well as custom query methods for message retrieval.
 */
@Repository
public interface MessageJdbcRepository extends JpaRepository<Message, UUID> {

    /**
     * Retrieves all messages associated with a given profile.
     *
     * @param profileId unique identifier of the profile
     * @return list of messages belonging to the specified profile
     */
    List<Message> findAllByProfileId(UUID profileId);

    /**
     * Retrieves all messages associated with a given thread.
     *
     * @param threadId unique identifier of the thread
     * @return list of messages belonging to the specified thread
     */
    List<Message> findAllByThreadId(UUID threadId);

    /**
     * Retrieves paginated messages belonging to a given thread.
     *
     * @param threadId unique identifier of the thread
     * @param pageable pagination and sorting information
     * @return list of messages belonging to the specified thread
     */
    Page<Message> findAllByThreadId(UUID threadId, Pageable pageable);
}
