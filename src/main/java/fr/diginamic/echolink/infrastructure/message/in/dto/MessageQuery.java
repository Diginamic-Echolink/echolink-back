package fr.diginamic.echolink.infrastructure.message.in.dto;

import fr.diginamic.echolink.domain.profile.Profile;

public record MessageQuery(
    String id,
    String text,
    String profileId,
    String threadId) {
}
