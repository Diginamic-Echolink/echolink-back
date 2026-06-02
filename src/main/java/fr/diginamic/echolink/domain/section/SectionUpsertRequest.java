package fr.diginamic.echolink.domain.section;

import jakarta.validation.constraints.NotBlank;

public record SectionUpsertRequest(@NotBlank String name, @NotBlank String topic) {}
