package fr.diginamic.echolink.infrastructure.message.in.mapper;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.infrastructure.message.in.dto.MessageQuery;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting {@link Message} domain objects
 * into {@link MessageQuery} DTOs used for API responses.
 * <p>
 * This class isolates the transformation logic between the domain model
 * and the infrastructure layer, ensuring separation of concerns.
 */
@Component
public class MessageQueryMapper {

    /**
     * Converts a {@link Message} domain object into a {@link MessageQuery} DTO.
     *
     * @param message the message domain entity to convert
     * @return a DTO representing the message for API responses
     */
    public MessageQuery toQuery(Message message) {

        String profileId = message.getProfile() != null
                ? message.getProfile().getId().toString()
                : null;

        return new MessageQuery(
                message.getId().toString(),
                message.getText(),
                message.getCreatedAt(),
                profileId,
                message.getThread().getId().toString()
        );
    }
}
