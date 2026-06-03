package fr.diginamic.echolink.domain.thread;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ThreadUpsertRequest(
        @NotBlank UUID sectionId,
        @NotBlank UUID profileId,
        @NotNull String title,
        @NotNull String subject) {
}
