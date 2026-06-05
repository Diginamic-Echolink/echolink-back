package fr.diginamic.echolink.domain.section;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents the information required to create or update a section.
 *
 * @param name section name
 * @param topic topic covered by the section
 */
public record SectionUpsertRequest(@NotBlank String name, @NotBlank String topic) {}
