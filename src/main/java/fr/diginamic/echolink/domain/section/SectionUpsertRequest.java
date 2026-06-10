package fr.diginamic.echolink.domain.section;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents the information required to create or update a section.
 *
 * @param name section name
 * @param topic topic covered by the section
 */
@Schema(description = "Payload used to create or update a section")
public record SectionUpsertRequest(

        @Schema(
                description = "Section name",
                example = "Weather",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String name,

        @Schema(
                description = "Topic covered by the section",
                example = "Meteorological forecasts and climate conditions",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String topic
) {
}
