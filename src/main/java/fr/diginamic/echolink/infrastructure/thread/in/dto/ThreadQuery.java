package fr.diginamic.echolink.infrastructure.thread.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Represents thread information returned to clients.
 *
 * @param id unique identifier of the thread
 * @param title thread title
 * @param subject thread content or subject
 * @param createdAt date and time when the thread was created
 * @param sectionId unique identifier of the associated section
 * @param profileId unique identifier of the profile who created the thread
 */
@Schema(description = "Thread information returned by the API (weather / air quality discussions)")
public record ThreadQuery(

        @Schema(
                description = "Thread UUID",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        String id,

        @Schema(
                description = "Thread title",
                example = "Weekly Weather Forecast - Lyon"
        )
        String title,

        @Schema(
                description = "Thread subject or content",
                example = "Discussion about upcoming weather conditions and temperature trends"
        )
        String subject,

        @Schema(
                description = "Thread creation date",
                example = "2026-06-10T14:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Section UUID",
                example = "550e8400-e29b-41d4-a716-446655440001"
        )
        String sectionId,

        @Schema(
                description = "Author profile UUID",
                example = "550e8400-e29b-41d4-a716-446655440002"
        )
        String profileId
) {
}
