package fr.diginamic.echolink.application.message.service;

import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadGetUseCase;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.message.MessageTestData.givenMessage1;
import static fr.diginamic.echolink.domain.message.MessageTestData.givenMessage2;
import static fr.diginamic.echolink.domain.message.MessageTestData.givenMessageCreateRequest;
import static fr.diginamic.echolink.domain.message.MessageTestData.givenMessageUpdateRequest;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository repository;
    @Mock
    private ThreadGetUseCase threadGetUseCase;
    @Mock
    private ProfileGetUseCase profileGetUseCase;
    @InjectMocks
    private MessageService service;

    @Test
    void should_return_message_by_id() throws MessageNotFoundException {
        // GIVEN
        UUID id = givenUUID();
        Message message = givenMessage1();

        when(repository.getById(id)).thenReturn(Optional.of(message));

        // WHEN
        Message result = service.getById(id);

        // THEN
        assertThat(result).isEqualTo(message);
        verify(repository).getById(id);
    }

    @Test
    void should_throw_when_message_not_found() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(MessageNotFoundException.class)
                .hasMessage("Message with id " + id + " not found");
    }

    @Test
    void should_return_messages_by_thread_id() throws ThreadNotFoundException {
        // GIVEN
        UUID threadId = givenUUID();

        Thread thread = givenThread1();

        List<Message> messages = List.of(givenMessage1(), givenMessage2());

        when(threadGetUseCase.getById(threadId)).thenReturn(thread);
        when(repository.getAllByThreadId(thread.getId())).thenReturn(messages);

        // WHEN
        List<Message> result = service.getAllByThreadId(threadId);

        // THEN
        assertThat(result).containsExactlyElementsOf(messages);

        verify(threadGetUseCase).getById(threadId);
        verify(repository).getAllByThreadId(thread.getId());
    }

    @Test
    void should_create_message() throws ThreadNotFoundException, ProfileNotFoundException {
        // GIVEN
        UUID threadId = givenUUID();
        UUID profileId = givenUUID();

        Thread thread = givenThread1();
        Profile profile = givenProfile1();

        MessageCreateRequest request = givenMessageCreateRequest(threadId, profileId);

        when(threadGetUseCase.getById(threadId)).thenReturn(thread);
        when(profileGetUseCase.getById(profileId)).thenReturn(profile);

        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);

        // WHEN
        service.create(request);

        // THEN
        verify(repository).save(captor.capture());

        Message saved = captor.getValue();

        assertThat(saved.getText()).isEqualTo(request.text());
        assertThat(saved.getProfile()).isEqualTo(profile);
        assertThat(saved.getThread()).isEqualTo(thread);
    }

    @Test
    void should_update_message_when_owner() throws MessageNotFoundException, ProfileNotAllowedException {
        // GIVEN
        UUID messageId = givenUUID();

        Profile profile = givenProfile1();
        Message message = givenMessage1();
        message.setProfile(profile);

        MessageUpdateRequest request = givenMessageUpdateRequest();

        when(repository.getById(messageId)).thenReturn(Optional.of(message));
        when(repository.save(message)).thenReturn(message);

        // WHEN
        Message result = service.update(profile, messageId, request);

        // THEN
        assertThat(result.getText()).isEqualTo(request.text());
        verify(repository).save(message);
    }


    @Test
    void should_throw_when_user_not_allowed_to_update() {
        // GIVEN
        UUID messageId = givenUUID();

        Profile owner = givenProfile1();
        Profile attacker = givenProfile1();
        attacker.setId(UUID.randomUUID());

        Message message = givenMessage1();
        message.setProfile(owner);

        when(repository.getById(messageId)).thenReturn(Optional.of(message));

        // WHEN / THEN
        assertThatThrownBy(() -> service.update(attacker, messageId, givenMessageUpdateRequest()))
                .isInstanceOf(ProfileNotAllowedException.class)
                .hasMessage("You are not allowed to modify this message");
    }

    @Test
    void should_soft_delete_message() throws MessageNotFoundException, ProfileNotAllowedException {
        // GIVEN
        UUID messageId = givenUUID();

        Profile profile = givenProfile1();
        Message message = givenMessage1();
        message.setProfile(profile);

        when(repository.getById(messageId)).thenReturn(Optional.of(message));
        when(repository.save(message)).thenReturn(message);

        // WHEN
        service.delete(profile, messageId);

        // THEN
        assertThat(message.getText()).isEqualTo("This message was deleted by the moderator or the user");

        verify(repository).save(message);
    }

    @Test
    void should_throw_when_not_allowed_to_delete() {
        // GIVEN
        UUID messageId = givenUUID();

        Profile owner = givenProfile1();
        Profile attacker = givenProfile1();
        attacker.setId(UUID.randomUUID());

        Message message = givenMessage1();
        message.setProfile(owner);

        when(repository.getById(messageId)).thenReturn(Optional.of(message));

        // WHEN / THEN
        assertThatThrownBy(() -> service.delete(attacker, messageId))
                .isInstanceOf(ProfileNotAllowedException.class)
                .hasMessage("You are not allowed to delete this message");

        verify(repository, never()).save(any());
    }
}
