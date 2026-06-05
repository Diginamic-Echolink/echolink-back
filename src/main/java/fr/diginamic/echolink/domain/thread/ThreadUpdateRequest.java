package fr.diginamic.echolink.domain.thread;

import java.util.UUID;

public record ThreadUpdateRequest(
        String title,
        String subject,
        UUID sectionId) {
}
