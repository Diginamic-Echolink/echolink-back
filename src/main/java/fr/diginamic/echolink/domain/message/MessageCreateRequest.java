package fr.diginamic.echolink.domain.message;

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
public record MessageCreateRequest(
        @NotNull(message = "threadId is required") UUID threadId,
        @NotNull(message = "profileId is required") UUID profileId,
        @NotBlank(message = "text is required") String text) {
}
