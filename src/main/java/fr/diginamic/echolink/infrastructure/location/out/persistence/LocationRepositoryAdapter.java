package fr.diginamic.echolink.infrastructure.location.out.persistence;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.persistence.repository.LocationJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LocationRepositoryAdapter implements LocationRepository {

    private final LocationJdbcRepository repository;

    @Override
    public Set<String> findAllInseeCodes() {
        return repository.findAllInseeCodes();
    }

    @Override
    public void saveAll(List<Location> locations) {
        repository.saveAll(locations);
    }
}
