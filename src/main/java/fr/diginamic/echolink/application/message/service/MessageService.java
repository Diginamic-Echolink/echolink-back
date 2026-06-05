package fr.diginamic.echolink.application.message.service;

import fr.diginamic.echolink.application.message.port.in.MessageCreateUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageDeleteUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageGetUseCase;
import fr.diginamic.echolink.application.message.port.in.MessageUpdateUseCase;
import fr.diginamic.echolink.application.message.port.out.MessageRepository;
import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadGetUseCase;
import fr.diginamic.echolink.domain.message.Message;
import fr.diginamic.echolink.domain.message.MessageCreateRequest;
import fr.diginamic.echolink.domain.message.MessageUpdateRequest;
import fr.diginamic.echolink.domain.message.exception.MessageAccessDeniedException;
import fr.diginamic.echolink.domain.message.exception.MessageNotFoundsException;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService implements MessageGetUseCase, MessageCreateUseCase, MessageUpdateUseCase, MessageDeleteUseCase {

    private final ThreadGetUseCase threadGetUseCase;
    private final ProfileGetUseCase profileGetUseCase;

    private final MessageRepository repository;


    @Override
    public Message getById(UUID id) throws MessageNotFoundsException {
        return repository.getById(id)
                .orElseThrow(() -> new MessageNotFoundsException("Message with id " + id + " not found"));
    }

    @Override
    public List<Message> getAllByThread(UUID id) {
        return repository.getAllByThread(id);
    }

    @Override
    public Message create(MessageCreateRequest request) throws ThreadNotFoundException, ProfileNotFoundException {
        Thread thread = threadGetUseCase.getById(request.threadId());
        Profile profile = profileGetUseCase.getById(request.profileId());

        Message message = new Message(
                request.text(),
                profile,
                thread
        );
        return repository.save(message);
    }

    @Override
    public Message update(UUID id, MessageUpdateRequest request) throws ThreadNotFoundException, ProfileNotFoundException, MessageAccessDeniedException, MessageNotFoundsException {
        Message message = getById(id);

        if (request.text() != null && !request.text().isBlank()) {
            message.setText(request.text());
        }

        if (request.threadId() != null) {
            Thread thread = threadGetUseCase.getById(request.threadId());
            message.setThread(thread);
        }

        if (request.profileId() != null) {
            Profile profile = profileGetUseCase.getById(request.profileId());
            message.setProfile(profile);
            /*if (request.profileId().equals(message.getProfile().getId()) && !request.isAdmin()) {
                Profile profile = profileGetUseCase.getById(request.profileId());
                message.setProfile(profile);
            } else if (!request.profileId().equals(message.getProfile().getId()) && request.isAdmin()) {

            } else {
                throw new MessageAccessDeniedException("You are not allowed to edit this message");
            }

            {
                text : fjhfejke
                profileId : 1216546-45646cdd-vdvldvmd
                thread : 1216546-45646cdd-vdvldvmd
                isAdmin : true
            }*/
        }

        return repository.save(message);
    }

    @Override
    public void delete(UUID id) throws MessageNotFoundsException {
        Message message = getById(id);
        message.setText("This message was deleted by the moderator or the user");

        repository.save(message);
    }
}
