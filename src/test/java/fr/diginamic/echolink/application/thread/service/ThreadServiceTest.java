package fr.diginamic.echolink.application.thread.service;

import fr.diginamic.echolink.application.profile.port.in.ProfileGetUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionGetUseCase;
import fr.diginamic.echolink.application.thread.port.out.ThreadRepository;
import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.profile.exception.ProfileNotAllowedException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile1;
import static fr.diginamic.echolink.domain.profile.ProfileTestData.givenProfile3;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection1;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection2;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread1;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThread2;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThreadCreateRequest;
import static fr.diginamic.echolink.domain.thread.ThreadTestData.givenThreadUpdateRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThreadServiceTest {

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

        when(repository.getById(id)).thenReturn(Optional.of(thread));

        // WHEN
        Thread result = service.getById(id);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(thread.getId());
        assertThat(result.getTitle()).isEqualTo(thread.getTitle());
        assertThat(result.getSubject()).isEqualTo(thread.getSubject());
        assertThat(result.getCreatedAt()).isEqualTo(thread.getCreatedAt());
        assertThat(result.getSection().getId()).isEqualTo(thread.getSection().getId());
        assertThat(result.getProfile().getId()).isEqualTo(thread.getProfile().getId());

        verify(repository).getById(id);
    }

    @Test
    void should_throw_exception_when_thread_not_found() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id)).thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(ThreadNotFoundException.class)
                .hasMessage("Thread not found : " + id);

        verify(repository).getById(id);
    }

    @Test
    void should_return_all_threads_of_section() throws SectionNotFoundException {
        // GIVEN
        UUID sectionId = givenUUID();

        Section section = givenSection1();
        List<Thread> threads = List.of(givenThread1(), givenThread2());

        when(sectionGetUseCase.getById(sectionId)).thenReturn(section);
        when(repository.getAllBySectionId(section.getId())).thenReturn(threads);

        // WHEN
        List<Thread> result = service.getAllBySectionId(sectionId);

        // THEN
        assertThat(result).hasSize(2);

        assertThat(result.getFirst().getId()).isEqualTo(threads.getFirst().getId());
        assertThat(result.getFirst().getTitle()).isEqualTo(threads.getFirst().getTitle());
        assertThat(result.getFirst().getSubject()).isEqualTo(threads.getFirst().getSubject());

        assertThat(result.get(1).getId()).isEqualTo(threads.get(1).getId());
        assertThat(result.get(1).getTitle()).isEqualTo(threads.get(1).getTitle());
        assertThat(result.get(1).getSubject()).isEqualTo(threads.get(1).getSubject());

        verify(sectionGetUseCase).getById(sectionId);
        verify(repository).getAllBySectionId(section.getId());
    }

    @Test
    void should_return_paginated_threads_by_section() throws SectionNotFoundException {
        // GIVEN
        UUID sectionId = givenUUID();
        Pageable pageable = Pageable.ofSize(10);

        Section section = givenSection1();
        Page<Thread> page = Page.empty();

        when(sectionGetUseCase.getById(sectionId)).thenReturn(section);
        when(repository.getAllBySectionId(section.getId(), pageable)).thenReturn(page);

        // WHEN
        Page<Thread> result = service.getAllBySectionId(sectionId, pageable);

        // THEN
        assertThat(result).isNotNull();

        verify(sectionGetUseCase).getById(sectionId);
        verify(repository).getAllBySectionId(section.getId(), pageable);
    }

    @Test
    void should_create_thread_with_correct_values()
            throws SectionNotFoundException, ProfileNotFoundException {

        // GIVEN
        UUID sectionId = givenUUID();
        UUID profileId = givenUUID();

        Section section = givenSection1();
        Profile profile = givenProfile1();

        ThreadCreateRequest request = givenThreadCreateRequest(sectionId, profileId);

        when(sectionGetUseCase.getById(sectionId)).thenReturn(section);
        when(profileGetUseCase.getById(profileId)).thenReturn(profile);

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Thread> captor = ArgumentCaptor.forClass(Thread.class);

        // WHEN
        service.create(request);

        // THEN
        verify(repository).save(captor.capture());

        Thread saved = captor.getValue();

        assertThat(saved.getTitle()).isEqualTo(request.title());
        assertThat(saved.getSubject()).isEqualTo(request.subject());
        assertThat(saved.getSection().getId()).isEqualTo(section.getId());
        assertThat(saved.getProfile().getId()).isEqualTo(profile.getId());
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getMessages()).isNotNull();
        assertThat(saved.getMessages()).isEmpty();
    }

    @Test
    void should_throw_when_section_not_found_on_create() throws SectionNotFoundException {
        UUID sectionId = givenUUID();
        UUID profileId = givenUUID();

        ThreadCreateRequest request = givenThreadCreateRequest(sectionId, profileId);

        when(sectionGetUseCase.getById(sectionId))
                .thenThrow(new SectionNotFoundException("Section not found"));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(SectionNotFoundException.class);
    }

    @Test
    void should_throw_when_profile_not_found_on_create() throws SectionNotFoundException, ProfileNotFoundException {
        UUID sectionId = givenUUID();
        UUID profileId = givenUUID();

        Section section = givenSection1();
        ThreadCreateRequest request = givenThreadCreateRequest(sectionId, profileId);

        when(sectionGetUseCase.getById(sectionId)).thenReturn(section);
        when(profileGetUseCase.getById(profileId))
                .thenThrow(new ProfileNotFoundException("Profile not found"));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ProfileNotFoundException.class);
    }

    @Test
    void should_update_thread_when_profile_is_owner() throws Exception {
        // GIVEN
        UUID threadId = givenUUID();

        Profile profile = givenProfile1();
        Thread thread = givenThread1();
        thread.setProfile(profile);

        ThreadUpdateRequest request = givenThreadUpdateRequest(givenUUID());

        when(repository.getById(threadId)).thenReturn(Optional.of(thread));

        when(sectionGetUseCase.getById(any())).thenReturn(givenSection2());

        when(repository.save(thread)).thenReturn(thread);

        // WHEN
        Thread result = service.update(profile, threadId, request);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(thread.getId());
        assertThat(result.getTitle()).isEqualTo(request.title());
        assertThat(result.getSubject()).isEqualTo(request.subject());
        assertThat(result.getSection().getId()).isEqualTo(givenSection2().getId());
        assertThat(result.getProfile().getId()).isEqualTo(profile.getId());

        verify(repository).save(thread);
    }

    @Test
    void should_throw_when_profile_not_allowed() {
        // GIVEN
        UUID threadId = givenUUID();

        Profile owner = givenProfile1();
        Profile attacker = givenProfile1();
        attacker.setId(UUID.randomUUID());

        Thread thread = givenThread1();
        thread.setProfile(owner);

        ThreadUpdateRequest request = givenThreadUpdateRequest(givenUUID());

        when(repository.getById(threadId)).thenReturn(Optional.of(thread));

        // WHEN / THEN
        assertThatThrownBy(() -> service.update(attacker, threadId, request))
                .isInstanceOf(ProfileNotAllowedException.class)
                .hasMessage("You are not allowed to modify this thread");
    }

    @Test
    void should_allow_admin_to_update_any_thread() throws Exception {
        // GIVEN
        UUID threadId = givenUUID();

        Profile admin = givenProfile3();

        Profile owner = givenProfile1();

        Thread thread = givenThread1();
        thread.setProfile(owner);

        ThreadUpdateRequest request = givenThreadUpdateRequest(givenUUID());

        when(repository.getById(threadId)).thenReturn(Optional.of(thread));
        when(sectionGetUseCase.getById(any())).thenReturn(givenSection2());
        when(repository.save(thread)).thenReturn(thread);

        // WHEN
        Thread result = service.update(admin, threadId, request);

        // THEN
        assertThat(result).isNotNull();
        verify(repository).save(thread);
    }

    @Test
    void should_soft_delete_thread() throws Exception {
        // GIVEN
        UUID id = givenUUID();

        Profile profile = givenProfile1();
        Thread thread = givenThread1();
        thread.setProfile(profile);

        when(repository.getById(id)).thenReturn(Optional.of(thread));
        when(repository.save(thread)).thenReturn(thread);

        // WHEN
        service.delete(profile, id);

        // THEN
        assertThat(thread.getTitle()).isEqualTo("This thread has been deleted");
        assertThat(thread.getSubject()).isEqualTo("This thread has been deleted");

        verify(repository).save(thread);
    }

    @Test
    void should_throw_when_delete_not_allowed() {
        // GIVEN
        UUID id = givenUUID();

        Profile owner = givenProfile1();
        Profile attacker = givenProfile1();
        attacker.setId(givenUUID());

        Thread thread = givenThread1();
        thread.setProfile(owner);

        when(repository.getById(id)).thenReturn(Optional.of(thread));

        // WHEN / THEN
        assertThatThrownBy(() -> service.delete(attacker, id))
                .isInstanceOf(ProfileNotAllowedException.class)
                .hasMessage("You are not allowed to delete this thread");

        verify(repository).getById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void should_allow_admin_to_delete_any_thread() throws Exception {
        // GIVEN
        UUID id = givenUUID();

        Profile admin = givenProfile3();

        Thread thread = givenThread1();
        thread.setProfile(givenProfile1());

        when(repository.getById(id)).thenReturn(Optional.of(thread));
        when(repository.save(thread)).thenReturn(thread);

        // WHEN
        service.delete(admin, id);

        // THEN
        assertThat(thread.getTitle()).isEqualTo("This thread has been deleted");
        verify(repository).save(thread);
    }
}
