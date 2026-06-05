package fr.diginamic.echolink.infrastructure.section.in.mapper;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.infrastructure.section.in.dto.SectionQuery;
import org.springframework.stereotype.Component;

/**
 * Maps section domain objects to section query DTOs.
 */
@Component
public class SectionQueryMapper {

    /**
     * Converts a section domain object into a section query DTO.
     *
     * @param section section domain object to convert
     * @return corresponding section query DTO
     */
    public SectionQuery toQuery(Section section) {

        return new SectionQuery(
                section.getId().toString(),
                section.getName(),
                section.getTopic()
        );
    }
}
