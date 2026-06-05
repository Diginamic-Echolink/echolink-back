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

/**
 * REST controller exposing section management endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/section", produces = MediaType.APPLICATION_JSON_VALUE)
public class SectionController {

    /**
     * Use case responsible for retrieving sections.
     */
    private final SectionGetUseCase getUseCase;

    /**
     * Use case responsible for creating sections.
     */
    private final SectionCreateUseCase postUseCase;

    /**
     * Use case responsible for updating sections.
     */
    private final SectionUpdateUseCase updateUseCase;

    /**
     * Use case responsible for deleting sections.
     */
    private final SectionDeleteUseCase deleteUseCase;

    /**
     * Mapper used to convert section domain objects into query DTOs.
     */
    private final SectionQueryMapper mapper;

    /**
     * Retrieves all available sections.
     *
     * @return list of section information
     */
    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<SectionQuery>> getAllSections() {
        List<Section> sections = getUseCase.getAllSections();
        List<SectionQuery> query = sections.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

    /**
     * Creates a new section.
     *
     * @param request request containing section information
     * @return created section information
     */
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SectionQuery> createSection(@Valid @RequestBody SectionUpsertRequest request) {
        Section section = postUseCase.create(request);
        SectionQuery query = mapper.toQuery(section);
        return ResponseEntity.ok(query);
    }

    /**
     * Updates an existing section.
     *
     * @param sectionId unique identifier of the section to update
     * @param request request containing updated section information
     * @return updated section information
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
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

    /**
     * Deletes a section.
     *
     * @param sectionId unique identifier of the section to delete
     * @return success response
     * @throws SectionNotFoundException if no section is found with the specified identifier
     */
    @DeleteMapping("/{sectionId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MessageQuery> updateSection(@PathVariable UUID sectionId) throws SectionNotFoundException {
        deleteUseCase.delete(sectionId);
        return ResponseEntity.ok(new MessageQuery("Section with id: " + sectionId + " is correctly deleted"));
    }
}
