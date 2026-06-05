package fr.diginamic.echolink.infrastructure.section.in;

import fr.diginamic.echolink.application.section.port.in.SectionDeleteUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionGetUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionCreateUseCase;
import fr.diginamic.echolink.application.section.port.in.SectionUpdateUseCase;
import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.domain.section.SectionUpsertRequest;
import fr.diginamic.echolink.domain.section.exception.SectionNotFoundException;
import fr.diginamic.echolink.infrastructure.common.in.dto.MessageQuery;
import fr.diginamic.echolink.infrastructure.section.in.dto.SectionQuery;
import fr.diginamic.echolink.infrastructure.section.in.mapper.SectionQueryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/section", produces = MediaType.APPLICATION_JSON_VALUE)
public class SectionController {

    private final SectionGetUseCase getUseCase;
    private final SectionCreateUseCase postUseCase;
    private final SectionUpdateUseCase updateUseCase;
    private final SectionDeleteUseCase deleteUseCase;

    private final SectionQueryMapper mapper;

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<SectionQuery>> getAllSections() {
        List<Section> sections = getUseCase.getAllSections();
        List<SectionQuery> query = sections.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SectionQuery> createSection(@Valid @RequestBody SectionUpsertRequest request) {
        Section section = postUseCase.create(request);
        SectionQuery query = mapper.toQuery(section);
        return ResponseEntity.ok(query);
    }

    @PutMapping("/{sectionId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SectionQuery> updateSection(
            @PathVariable UUID sectionId,
            @Valid @RequestBody SectionUpsertRequest request
    ) throws SectionNotFoundException {
        Section section = updateUseCase.update(sectionId, request);
        SectionQuery query = mapper.toQuery(section);
        return ResponseEntity.ok(query);
    }

    @DeleteMapping("/{sectionId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageQuery> updateSection(@PathVariable UUID sectionId) throws SectionNotFoundException {
        deleteUseCase.delete(sectionId);
        return ResponseEntity.ok(new MessageQuery("Section with id: " + sectionId + " is correctly deleted"));
    }
}
