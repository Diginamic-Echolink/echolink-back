package fr.diginamic.echolink.domain.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @Email(message = "email need to be valid") String email,
        @NotBlank(message = "password is required") String password) {
}
