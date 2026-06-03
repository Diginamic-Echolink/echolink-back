package fr.diginamic.echolink.infrastructure.thread.in.mapper;

import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.infrastructure.thread.in.dto.ThreadQuery;
import org.springframework.stereotype.Component;

@Component
public class ThreadQueryMapper {

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
