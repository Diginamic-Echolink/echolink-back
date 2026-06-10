package fr.diginamic.echolink.domain.message;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request object used to update an existing {@link Message}.
 * <p>
 * Only mutable fields of a message are included here. Fields that are null
 * or blank are ignored during the update process.
 *
 * @param text new content of the message
 */
@Schema(description = "Payload used to update an existing message")
public record MessageUpdateRequest(

        @Schema(
                description = "New message content",
                example = "Updated message content"
        )
        String text
) {
}
