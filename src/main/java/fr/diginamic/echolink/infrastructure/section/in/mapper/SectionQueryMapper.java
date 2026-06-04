package fr.diginamic.echolink.infrastructure.section.in.mapper;

import fr.diginamic.echolink.domain.section.Section;
import fr.diginamic.echolink.infrastructure.section.in.dto.SectionQuery;
import org.springframework.stereotype.Component;

@Component
public class SectionQueryMapper {

    public SectionQuery toQuery(Section section) {

        return new SectionQuery(
                section.getId().toString(),
                section.getName(),
                section.getTopic()
        );
    }
}
