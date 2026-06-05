package fr.diginamic.echolink.application.thread.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionGetUseCase;
import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotFoundException;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.domain.thread.ThreadCreateRequest;
import fr.diginamic.echolink.domain.thread.ThreadUpdateRequest;
import fr.diginamic.echolink.domain.thread.exception.ThreadNotFoundException;
import fr.diginamic.echolink.domain.thread.Thread;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection1;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection2;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread1;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread2;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThreadCreateRequest;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThreadPartialUpdateRequest;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThreadUpdateRequest;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThreadWithBlankTitleUpdateRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ThreadServiceTest {

    @Mock
    private ThreadRepository repository;
    @Mock
    private SectionGetUseCase sectionGetUseCase;
    @Mock
    private ProfileGetUseCase profileGetUseCase;
    @InjectMocks
    private ThreadService service;

    @Test
    void should_return_thread_by_id() throws ThreadNotFoundException {
        // GIVEN
        UUID id = givenUUID();

        Thread thread = givenThread1();

        when(repository.getById(id))
                .thenReturn(Optional.of(thread));

        // WHEN
        Thread result = service.getById(id);

        // THEN
        assertThat(result).isEqualTo(thread);

        verify(repository).getById(id);
    }

    @Test
    void should_throw_exception_when_thread_not_found() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(ThreadNotFoundException.class)
                .hasMessage("Thread not found : " + id);
    }

    @Test
    void should_return_all_threads_of_section() {
        // GIVEN
        UUID sectionId = givenUUID();

        List<Thread> threads = List.of(
                givenThread1(),
                givenThread2()
        );

        when(repository.getAllBySectionId(sectionId))
                .thenReturn(threads);

        // WHEN
        List<Thread> result = service.getAllBySectionId(sectionId);

        // THEN
        assertThat(result)
                .hasSize(2)
                .containsExactlyElementsOf(threads);

        verify(repository).getAllBySectionId(sectionId);
    }

    @Test
    void should_create_thread_with_correct_values() throws SectionNotFoundException, ProfileNotFoundException {
        // GIVEN
        UUID sectionId = givenUUID();
        UUID profileId = givenUUID();

        Section section = givenSection1();
        Profile profile = givenProfile1();

        ThreadCreateRequest request = givenThreadCreateRequest(sectionId, profileId);

        when(sectionGetUseCase.getById(sectionId))
                .thenReturn(section);

        when(profileGetUseCase.getById(profileId))
                .thenReturn(profile);

        when(repository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Thread> captor = ArgumentCaptor.forClass(Thread.class);

        // WHEN
        service.create(request);

        // THEN
        verify(repository).save(captor.capture());

        Thread saved = captor.getValue();

        assertThat(saved.getTitle()).isEqualTo(request.title());
        assertThat(saved.getSubject()).isEqualTo(request.subject());
        assertThat(saved.getSection()).isEqualTo(section);
        assertThat(saved.getProfile()).isEqualTo(profile);
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    void should_update_thread() throws SectionNotFoundException, ThreadNotFoundException {
        // GIVEN
        UUID threadId = givenUUID();
        UUID sectionId = givenUUID();

        Thread thread = givenThread1();
        Section newSection = givenSection2();

        ThreadUpdateRequest request = givenThreadUpdateRequest(sectionId);

        when(repository.getById(threadId))
                .thenReturn(Optional.of(thread));

        when(sectionGetUseCase.getById(sectionId))
                .thenReturn(newSection);

        when(repository.save(thread))
                .thenReturn(thread);

        // WHEN
        Thread result = service.update(threadId, request);

        // THEN
        assertThat(result.getTitle()).isEqualTo(request.title());
        assertThat(result.getSubject()).isEqualTo(request.subject());
        assertThat(result.getSection()).isEqualTo(newSection);

        verify(repository).save(thread);
    }

    @Test
    void should_partially_update_thread() throws ThreadNotFoundException, SectionNotFoundException {
        // GIVEN
        UUID threadId = givenUUID();

        Thread thread = givenThread1();

        String oldTitle = thread.getTitle();
        Section oldSection = thread.getSection();

        ThreadUpdateRequest request = givenThreadPartialUpdateRequest();

        when(repository.getById(threadId))
                .thenReturn(Optional.of(thread));

        when(repository.save(thread))
                .thenReturn(thread);

        // WHEN
        Thread result = service.update(threadId, request);

        // THEN
        assertThat(result.getTitle()).isEqualTo(oldTitle);
        assertThat(result.getSubject()).isEqualTo(request.subject());
        assertThat(result.getSection()).isEqualTo(oldSection);
    }

    @Test
    void should_ignore_blank_title() throws ThreadNotFoundException, SectionNotFoundException {
        // GIVEN
        UUID threadId = givenUUID();

        Thread thread = givenThread1();

        ThreadUpdateRequest request = givenThreadWithBlankTitleUpdateRequest();

        when(repository.getById(threadId))
                .thenReturn(Optional.of(thread));

        when(repository.save(thread))
                .thenReturn(thread);

        // WHEN
        Thread result = service.update(threadId, request);

        // THEN
        assertThat(result.getTitle()).isEqualTo(thread.getTitle());
    }

    @Test
    void should_soft_delete_thread() throws ThreadNotFoundException {
        // GIVEN
        UUID id = givenUUID();

        Thread thread = givenThread1();

        when(repository.getById(id))
                .thenReturn(Optional.of(thread));

        when(repository.save(thread))
                .thenReturn(thread);

        // WHEN
        service.delete(id);

        // THEN
        assertThat(thread.getTitle()).isEqualTo("This thread has been deleted");
        assertThat(thread.getSubject()).isEqualTo("This thread has been deleted");

        verify(repository).save(thread);
    }

    @Test
    void should_throw_exception_when_deleting_unknown_thread() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ThreadNotFoundException.class)
                .hasMessage("Thread not found : " + id);

        verify(repository, never()).save(any());
    }
}
