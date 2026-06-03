package fr.diginamic.echolink.infrastructure.demography.in;


import fr.diginamic.echolink.application.demography.port.in.DemographyGetUseCase;
import fr.diginamic.echolink.domain.demography.Demography;
import fr.diginamic.echolink.infrastructure.demography.in.dto.DemographyQuery;
import fr.diginamic.echolink.infrastructure.demography.in.mapper.DemographyQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/demography", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemographyController {

    private final DemographyGetUseCase getUseCase;
    private final DemographyQueryMapper mapper;

    @GetMapping("/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<DemographyQuery> getDemographyByLocationId(@PathVariable UUID locationId) {
        Demography demographies = getUseCase.getByLocationId(locationId);
        DemographyQuery query = mapper.toQuery(demographies);
        return ResponseEntity.ok(query);
    }

    @GetMapping("/all/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<DemographyQuery>> getAllByLocationId(@PathVariable UUID locationId) {
        List<Demography> demographies = getUseCase.getAllByLocationId(locationId);
        List<DemographyQuery> query = demographies.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }

}
