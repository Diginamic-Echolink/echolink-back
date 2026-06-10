package fr.diginamic.echolink.infrastructure.profile.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents the response returned after a successful authentication.
 *
 * @param token generated authentication token
 */
@Schema(description = "Authentication response containing JWT token")
public record AuthResponse(

        @Schema(
                description = "JWT authentication token generated after successful login or registration",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String token
) {
}
