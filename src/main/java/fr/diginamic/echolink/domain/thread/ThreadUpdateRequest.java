package fr.diginamic.echolink.domain.thread;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * Represents the information used to update a thread.
 *
 * @param title updated thread title
 * @param subject updated thread content or subject
 * @param sectionId unique identifier of the associated section
 */
@Schema(description = "Payload used to update a weather or air quality thread")
public record ThreadUpdateRequest(

        @Schema(
                description = "Updated thread title",
                example = "Updated Weather Forecast - Lyon"
        )
        String title,

        @Schema(
                description = "Updated subject or description",
                example = "Updated analysis of temperature drop and wind conditions"
        )
        String subject,

        @Schema(
                description = "Updated section UUID",
                example = "550e8400-e29b-41d4-a716-446655440001"
        )
        UUID sectionId
) {
}
