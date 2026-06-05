package fr.diginamic.echolink.application.section.service;

import fr.diginamic.echolink.application.section.port.out.SectionRepository;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection1;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection2;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSection3;
import static fr.diginamic.echolink.domain.section.SectionTestData.givenSectionUpsertRequest;
import static fr.diginamic.echolink.domain.shared.SharedTestData.givenUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

    @Mock
    private SectionRepository repository;
    @InjectMocks
    private SectionService service;

    @Test
    void should_return_section_by_id() throws SectionNotFoundException {
        // GIVEN
        UUID id = givenUUID();
        Section section = givenSection1();

        when(repository.getById(id))
                .thenReturn(Optional.of(section));

        // WHEN
        Section result = service.getById(id);

        // THEN
        assertThat(result).isEqualTo(section);

        verify(repository).getById(id);
    }

    @Test
    void should_throw_exception_when_section_not_found() {
        // GIVEN
        UUID id = UUID.randomUUID();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(SectionNotFoundException.class)
                .hasMessage("Section not found : " + id);
    }

    @Test
    void should_return_all_sections() {
        // GIVEN
        List<Section> sections = List.of(
                givenSection1(),
                givenSection2(),
                givenSection3()
        );

        when(repository.getAllSections())
                .thenReturn(sections);

        // WHEN
        List<Section> result = service.getAllSections();

        // THEN
        assertThat(result)
                .hasSize(3)
                .containsExactlyElementsOf(sections);

        verify(repository).getAllSections();
    }

    @Test
    void should_create_section_with_request_values() {
        // GIVEN
        SectionUpsertRequest request = givenSectionUpsertRequest();

        when(repository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Section> captor = ArgumentCaptor.forClass(Section.class);

        // WHEN
        service.create(request);

        // THEN
        verify(repository).save(captor.capture());

        Section saved = captor.getValue();

        assertThat(saved.getName()).isEqualTo(request.name());
        assertThat(saved.getTopic()).isEqualTo(request.topic());
    }

    @Test
    void should_update_section() throws SectionNotFoundException {
        // GIVEN
        UUID id = givenUUID();

        Section section = givenSection1();

        SectionUpsertRequest request = givenSectionUpsertRequest();

        when(repository.getById(id))
                .thenReturn(Optional.of(section));

        when(repository.save(section))
                .thenReturn(section);

        // WHEN
        Section result = service.update(id, request);

        // THEN
        assertThat(result.getName()).isEqualTo(request.name());
        assertThat(result.getTopic()).isEqualTo(request.topic());

        verify(repository).save(section);
    }

    @Test
    void should_throw_exception_when_updating_unknown_section() {
        // GIVEN
        UUID id = givenUUID();

        SectionUpsertRequest request = givenSectionUpsertRequest();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(SectionNotFoundException.class)
                .hasMessage("Section not found : " + id);

        verify(repository, never()).save(any());
    }

    @Test
    void should_delete_section() throws SectionNotFoundException {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.of(givenSection1()));

        // WHEN
        service.delete(id);

        // THEN
        verify(repository).delete(id);
    }

    @Test
    void should_throw_exception_when_deleting_unknown_section() {
        // GIVEN
        UUID id = givenUUID();

        when(repository.getById(id))
                .thenReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(SectionNotFoundException.class)
                .hasMessage("Section not found : " + id);

        verify(repository, never()).delete(any());
    }
}
