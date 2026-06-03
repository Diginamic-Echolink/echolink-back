package fr.diginamic.echolink.infrastructure.meteo.in;

import fr.diginamic.echolink.application.meteo.port.in.MeteoGetUseCase;
import fr.diginamic.echolink.domain.meteo.Meteo;
import fr.diginamic.echolink.infrastructure.meteo.in.dto.MeteoQuery;
import fr.diginamic.echolink.infrastructure.meteo.in.mapper.MeteoQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/meteo", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeteoController {

    private final MeteoGetUseCase getUseCase;
    private final MeteoQueryMapper mapper;

    @GetMapping("/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<MeteoQuery> getMeteoByLocationId(@PathVariable UUID locationId) {
        Meteo meteos = getUseCase.getMeteoByLocationId(locationId);
        MeteoQuery query = mapper.toQuery(meteos);
        return ResponseEntity.ok(query);
    }

    @GetMapping("/all/{locationId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<MeteoQuery>> getAllMeteoByLocationId(@PathVariable UUID locationId) {
        List<Meteo> meteos = getUseCase.getAllMeteoByLocationId(locationId);
        List<MeteoQuery> query = meteos.stream().map(mapper::toQuery).toList();
        return ResponseEntity.ok(query);
    }
}
