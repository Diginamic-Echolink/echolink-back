package fr.diginamic.echolink.infrastructure.thread.in.mapper;

import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.infrastructure.thread.in.dto.ThreadQuery;
import org.springframework.stereotype.Component;

/**
 * Maps thread domain objects to thread query DTOs.
 */
@Component
public class ThreadQueryMapper {

    /**
     * Converts a thread domain object into a thread query DTO.
     *
     * @param thread thread domain object to convert
     * @return corresponding thread query DTO
     */
    public ThreadQuery toQuery(Thread thread) {

        return new ThreadQuery(
                thread.getId().toString(),
                thread.getTitle(),
                thread.getSubject(),
                thread.getCreatedAt(),
                thread.getSection().getId().toString(),
                thread.getProfile().getId().toString()
        );
    }
}
