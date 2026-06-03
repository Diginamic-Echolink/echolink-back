package fr.diginamic.echolink.infrastructure.location.out.persistence;

import fr.diginamic.echolink.application.location.port.out.LocationRepository;
import fr.diginamic.echolink.domain.location.Location;
import fr.diginamic.echolink.infrastructure.location.out.persistence.repository.LocationJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocationRepositoryAdapter implements LocationRepository {

    private final LocationJdbcRepository repository;

    @Override
    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    @Override
    public Set<String> findAllInseeCodes() {
        return repository.findAllInseeCodes();
    }

    @Override
    public void saveAll(List<Location> locations) {
        repository.saveAll(locations);
    }

    public Optional<Location> getById(UUID id) {
        return repository.findById (id);
    }

    @Override
    public List<Location> getByGeo(float latitudeMin, float latitudeMax, float longitudeMin, float longitudeMax, int limit) {
        return repository.findLocationsByCordonneeBetween(latitudeMin, latitudeMax, longitudeMin, longitudeMax);
    }
}
