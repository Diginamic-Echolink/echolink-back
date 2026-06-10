package fr.diginamic.echolink.domain.thread;

import java.util.UUID;

/**
 * Represents the information used to update a thread.
 *
 * @param title updated thread title
 * @param subject updated thread content or subject
 * @param sectionId unique identifier of the associated section
 */
public record ThreadUpdateRequest(
        String title,
        String subject,
        UUID sectionId) {
}
