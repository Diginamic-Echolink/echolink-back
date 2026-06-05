package fr.diginamic.echolink.application.message.service;

import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundsException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.message.MessageTestData.givenMessage;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository repository;
    @InjectMocks
    private MessageService service;

    @Test
    void should_return_message_by_id() throws MessageNotFoundsException {
        // GIVEN
        UUID id = givenUUID();
        Message message = givenMessage();

        when(repository.getById(id))
                .thenReturn(Optional.of(message));

        // WHEN
        Message result = service.getById(id);

        // THEN
        assertThat(result).isEqualTo(message);

        verify(repository).getById(id);
    }

    @Test
    void should_throw_exception_when_thread_not_found() throws ThreadNotFoundException {
        //GIVEN

        UUID
    }

    @Test
    void should_return_all_messages() throws  {

    }

}
