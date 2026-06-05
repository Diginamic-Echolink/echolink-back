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
import fr.diginamic.echolink.domain.thread.ThreadCreateRequest;
import fr.diginamic.echolink.domain.thread.ThreadUpdateRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service responsible for thread retrieval, creation, update and deletion operations.
 */
@Service
@RequiredArgsConstructor
public class ThreadService implements ThreadGetUseCase, ThreadCreateUseCase, ThreadUpdateUseCase, ThreadDeleteUseCase {

    /**
     * Use case used to retrieve sections.
     */
    private final SectionGetUseCase sectionGetUseCase;

    /**
     * Use case used to retrieve profiles.
     */
    private final ProfileGetUseCase profileGetUseCase;

    /**
     * Repository used to access thread data.
     */
    private final ThreadRepository repository;

    /**
     * Retrieves a thread by its unique identifier.
     *
     * @param id unique identifier of the thread
     * @return the matching thread
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     */
    @Override
    public Thread getById(UUID id) throws ThreadNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new ThreadNotFoundException("Thread not found : " + id));
    }

    /**
     * Retrieves all threads associated with a section.
     *
     * @param sectionId unique identifier of the section
     * @return list of threads belonging to the section
     */
    @Override
    public List<Thread> getAllBySectionId(UUID sectionId) {
        return repository.getAllBySectionId(sectionId);
    }

    /**
     * Creates a new thread.
     *
     * @param request request containing thread information
     * @return the created thread
     * @throws ThreadCreationNotValidException if the thread data is invalid
     * @throws SectionNotFoundException if the associated section cannot be found
     * @throws ProfileNotFoundException if the associated profile cannot be found
     */
    @Override
    public Thread create(ThreadCreateRequest request) throws SectionNotFoundException, ProfileNotFoundException {
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

    /**
     * Updates an existing thread.
     *
     * @param id unique identifier of the thread to update
     * @param request request containing updated thread information
     * @return the updated thread
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     */
    @Override
    public Thread update(UUID id, ThreadUpdateRequest request) throws ThreadNotFoundException, SectionNotFoundException, ProfileNotFoundException{
        Thread thread = getById(id);

        if (request.title() != null && !request.title().isBlank()) {
            thread.setTitle(request.title());
        }
        if (request.subject() != null && !request.subject().isBlank()) {
            thread.setSubject(request.subject());
        }
        if (request.sectionId() != null) {
            Section section = sectionGetUseCase.getById(request.sectionId());
            thread.setSection(section);
        }

        if (request.profileId() != null) {
            Profile profile = profileGetUseCase.getById(request.profileId());
            thread.setProfile(profile);
        }

        return repository.save(thread);
    }

    /**
     * Marks a thread as deleted.
     *
     * @param id unique identifier of the thread to delete
     * @throws ThreadNotFoundException if no thread is found with the specified identifier
     */
    @Override
    public void delete(UUID id) throws ThreadNotFoundException {
        Thread thread = getById(id);

        thread.setTitle("This thread has been deleted");
        thread.setSubject("This thread has been deleted");

        repository.save(thread);
    }
}
