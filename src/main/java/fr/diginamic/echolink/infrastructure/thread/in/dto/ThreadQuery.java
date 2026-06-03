package fr.diginamic.echolink.infrastructure.thread.in.dto;

import java.time.LocalDateTime;

public record ThreadQuery(
        String id,
        String title,
        String subject,
        LocalDateTime createdAt,
        String sectionId,
        String profileId) {
}
