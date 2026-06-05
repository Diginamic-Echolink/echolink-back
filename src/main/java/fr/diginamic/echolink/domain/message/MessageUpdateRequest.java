package fr.diginamic.echolink.domain.message;

import fr.diginamic.echolink.domain.profile.Profile;
import fr.diginamic.echolink.domain.thread.Thread;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MessageUpdateRequest(
        String text,
        UUID profileId,
        UUID threadId
) {
}
