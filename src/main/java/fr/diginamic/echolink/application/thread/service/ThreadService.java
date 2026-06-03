package fr.diginamic.echolink.application.thread.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadCreateUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadDeleteUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadGetUseCase;
import fr.diginamic.echolink.application.thread.port.in.ThreadUpdateUseCase;
import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import fr.diginamic.echolink.domain.thread.ThreadUpsertRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadCreationNotValidException;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThreadService implements ThreadGetUseCase, ThreadCreateUseCase, ThreadUpdateUseCase, ThreadDeleteUseCase {

    private final SectionGetUseCase sectionGetUseCase;
    private final ProfileGetUseCase profileGetUseCase;

    private final ThreadRepository repository;

    @Override
    public Thread getById(UUID id) throws ThreadNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new ThreadNotFoundException("Thread not found : " + id));
    }

    @Override
    public List<Thread> getAllBySectionId(UUID sectionId) {
        return repository.getAllBySectionId(sectionId);
    }

    @Override
    public Thread create(
            ThreadUpsertRequest request
    ) throws ThreadCreationNotValidException, SectionNotFoundException, ProfileNotFoundException {

        if (request.title().isBlank()) {
            throw new ThreadCreationNotValidException("Title is required");
        }
        if (request.subject().isBlank()) {
            throw new ThreadCreationNotValidException("Subject is required");
        }

        Section section = sectionGetUseCase.getById(request.sectionId());
        Profile profile = profileGetUseCase.getById(request.profileId());

        Thread thread = new Thread(
                request.title(),
                request.subject(),
                LocalDateTime.now(),
                section,
                profile
        );
        return repository.save(thread);
    }

    @Override
    public Thread update(UUID id, ThreadUpsertRequest request) throws ThreadNotFoundException {
        Thread thread = getById(id);

        if (!request.title().isBlank()) {
            thread.setTitle(request.title());
        }
        if (!request.subject().isBlank()) {
            thread.setSubject(request.subject());
        }

        return repository.save(thread);
    }

    @Override
    public void delete(UUID id) throws ThreadNotFoundException {
        Thread thread = getById(id);

        thread.setTitle("This thread has been deleted");
        thread.setSubject("This thread has been deleted");

        repository.save(thread);
    }
}
