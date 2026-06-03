package fr.diginamic.echolink.infrastructure.demography.in.mapper;

import fr.diginamic.echolink.domain.demography.Demography;
import fr.diginamic.echolink.infrastructure.demography.in.dto.DemographyQuery;
import org.springframework.stereotype.Component;

@Component
public class DemographyQueryMapper {

    public DemographyQuery toQuery(Demography demography) {
        return new DemographyQuery(
                demography.getRecordedAt(),
                demography.getTotalPop()
        );
    }
}
