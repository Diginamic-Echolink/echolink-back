package fr.diginamic.echolink.domain.thread;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Represents the information required to create a thread.
 *
 * @param sectionId unique identifier of the associated section
 * @param profileId unique identifier of the profile creating the thread
 * @param title thread title
 * @param subject thread content or subject
 */
@Schema(description = "Payload used to create a weather or air quality discussion thread")
public record ThreadCreateRequest(

        @Schema(
                description = "Section UUID (Weather / Air Quality / Environmental Monitoring)",
                example = "550e8400-e29b-41d4-a716-446655440001",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "sectionId is required")
        UUID sectionId,

        @Schema(
                description = "Author profile UUID",
                example = "550e8400-e29b-41d4-a716-446655440002",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "profileId is required")
        UUID profileId,

        @Schema(
                description = "Thread title",
                example = "Air Quality in Paris Today",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "title is required")
        String title,

        @Schema(
                description = "Thread subject or description",
                example = "PM2.5 levels increased due to traffic and temperature inversion",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "subject is required")
        String subject
) {
}

