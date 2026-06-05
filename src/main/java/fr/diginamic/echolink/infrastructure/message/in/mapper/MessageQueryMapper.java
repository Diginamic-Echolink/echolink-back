package fr.diginamic.echolink.infrastructure.message.in.mapper;

import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.infrastructure.message.in.dto.MessageQuery;
import org.springframework.stereotype.Component;

@Component
public class MessageQueryMapper {

    public MessageQuery toQuery(Message message) {
        return new MessageQuery(
                message.getId().toString(),
                message.getText(),
                message.getProfile().getId().toString(),
                message.getThread().getId().toString()
        );
    }
}
