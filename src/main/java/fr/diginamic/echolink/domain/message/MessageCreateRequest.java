package fr.diginamic.echolink.domain.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MessageCreateRequest(
        @NotNull(message = "thredId is required") UUID threadId,
        @NotNull(message = "profileId is required") UUID profileId,
        @NotBlank(message = "text is required") String text
        ) {
}
