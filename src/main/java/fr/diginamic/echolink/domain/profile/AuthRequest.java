package fr.diginamic.echolink.domain.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents an authentication request containing user credentials.
 *
 * @param email email address used for authentication
 * @param password password associated with the account
 */
@Schema(description = "Authentication request payload used for login and registration")
public record AuthRequest(
        @Schema(
                description = "Email address used for authentication",
                example = "user@email.com"
        )
        @NotBlank(message = "email is required")
        @Email(message = "email need to be valid")
        String email,

        @Schema(
                description = "User password",
                example = "Str0ngP@ssword"
        )
        @NotBlank(message = "password is required")
        String password
) {
}
