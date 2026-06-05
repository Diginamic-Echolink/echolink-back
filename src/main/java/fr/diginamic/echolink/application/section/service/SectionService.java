package fr.diginamic.echolink.application.section.service;

import fr.diginamic.echolink.application.section.port.in.SectionDeleteUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionGetUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionCreateUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionUpdateUseCase;
import fr.diginamic.echolink.application.section.port.out.SectionRepository;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service responsible for section retrieval, creation, update and deletion operations.
 */
@Service
@RequiredArgsConstructor
public class SectionService
        implements SectionGetUseCase, SectionCreateUseCase, SectionUpdateUseCase, SectionDeleteUseCase {

    /**
     * Repository used to access section data.
     */
    private final SectionRepository repository;

    @Override
    public Section getById(UUID id) throws SectionNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new SectionNotFoundException("Section not found : " + id));
    }

    @Override
    public List<Section> getAllSections() {
        return repository.getAllSections();
    }

    @Override
    public Section create(SectionUpsertRequest request) {
        Section section = new Section(request.name(), request.topic());
        return repository.save(section);
    }

    @Override
    public Section update(UUID id, SectionUpsertRequest request) throws SectionNotFoundException {
        Section section = getById(id);
        section.setName(request.name());
        section.setTopic(request.topic());
        return repository.save(section);
    }

    @Override
    public void delete(UUID id) throws SectionNotFoundException {
        getById(id);
        repository.delete(id);
    }
}
