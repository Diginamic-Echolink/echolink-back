package fr.diginamic.echolink.infrastructure.section.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents section information.
 *
 * @param id unique identifier of the section
 * @param name section name
 * @param topic topic covered by the section
 */
@Schema(description = "Section information returned by the API")
public record SectionQuery(

        @Schema(
                description = "Section UUID",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        String id,

        @Schema(
                description = "Section name",
                example = "Weather"
        )
        String name,

        @Schema(
                description = "Topic covered by the section",
                example = "Real-time weather forecasts and climate data"
        )
        String topic
) {
}
