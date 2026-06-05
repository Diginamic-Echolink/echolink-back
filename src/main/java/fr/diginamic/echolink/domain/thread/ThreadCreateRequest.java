package fr.diginamic.echolink.domain.thread;

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
public record ThreadCreateRequest(
        @NotNull(message = "sectionId is required") UUID sectionId,
        @NotNull(message = "profileId is required") UUID profileId,
        @NotBlank(message = "title is required") String title,
        @NotBlank(message = "subject is required") String subject) {
}

