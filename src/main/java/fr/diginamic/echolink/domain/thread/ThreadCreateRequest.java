package fr.diginamic.echolink.domain.thread;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ThreadCreateRequest(
        @NotNull(message = "sectionId is required") UUID sectionId,
        @NotNull(message = "profileId is required") UUID profileId,
        @NotBlank(message = "title is required") String title,
        @NotBlank(message = "subject is required") String subject) {
}
