package fr.diginamic.echolink.domain.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Request object used to create a new {@link Message}.
 * <p>
 * Contains all required information to create a message in a thread,
 * including the author profile and the message content.
 *
 * @param threadId unique identifier of the thread where the message is posted
 * @param profileId unique identifier of the profile creating the message
 * @param text content of the message (must not be blank)
 */
@Schema(description = "Payload used to create a new message")
public record MessageCreateRequest(

        @Schema(
                description = "Thread UUID",
                example = "550e8400-e29b-41d4-a716-446655440000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "threadId is required")
        UUID threadId,

        @Schema(
                description = "Author profile UUID",
                example = "e7d3f4c2-9b8e-4f8d-9a5b-123456789abc",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "profileId is required")
        UUID profileId,

        @Schema(
                description = "Message content",
                example = "Hello everyone!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "text is required")
        String text
) {
}
