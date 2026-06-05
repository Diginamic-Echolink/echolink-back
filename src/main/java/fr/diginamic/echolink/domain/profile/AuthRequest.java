package fr.diginamic.echolink.domain.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents an authentication request containing user credentials.
 *
 * @param email email address used for authentication
 * @param password password associated with the account
 */
public record AuthRequest(
        @Email(message = "email need to be valid") String email,
        @NotBlank(message = "password is required") String password) {
}
