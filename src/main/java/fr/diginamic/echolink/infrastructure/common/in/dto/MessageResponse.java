package fr.diginamic.echolink.infrastructure.common.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a simple message response from the server.
 *
 * @param message message returned to the client
 */
@Schema(description = "Simple API response message")
public record MessageResponse(

        @Schema(
                description = "Response message",
                example = "Message with id: 550e8400-e29b-41d4-a716-446655440000 is correctly deleted"
        )
        String message
) {
}
