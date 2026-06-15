package fr.diginamic.echolink.infrastructure.message.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO representing a message returned by the API.
 * <p>
 * This object is used to expose message data to clients without
 * exposing the internal domain model.
 *
 * @param id unique identifier of the message
 * @param text content of the message
 * @param createdAt date and time when the message was created
 * @param profileId identifier of the author profile
 * @param threadId identifier of the thread containing the message
 */
@Schema(description = "Message returned by the API")
public record MessageQuery(

        @Schema(
                description = "Message UUID",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        String id,

        @Schema(
                description = "Message content",
                example = "Hello everyone!"
        )
        String text,

        @Schema(
                description = "Message creation date",
                example = "2026-06-10T14:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Author profile UUID",
                example = "e7d3f4c2-9b8e-4f8d-9a5b-123456789abc"
        )
        String profileId,

        @Schema(
                description = "Thread UUID",
                example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
        )
        String threadId
) {
}
