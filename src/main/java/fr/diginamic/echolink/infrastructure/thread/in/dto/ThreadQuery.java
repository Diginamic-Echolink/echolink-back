package fr.diginamic.echolink.infrastructure.thread.in.dto;

import java.time.LocalDateTime;

/**
 * Represents thread information returned to clients.
 *
 * @param id unique identifier of the thread
 * @param title thread title
 * @param subject thread content or subject
 * @param createdAt date and time when the thread was created
 * @param sectionId unique identifier of the associated section
 * @param profileId unique identifier of the profile who created the thread
 */
public record ThreadQuery(
        String id,
        String title,
        String subject,
        LocalDateTime createdAt,
        String sectionId,
        String profileId) {
}
