package fr.diginamic.echolink.infrastructure.section.in.dto;

/**
 * Represents section information.
 *
 * @param id unique identifier of the section
 * @param name section name
 * @param topic topic covered by the section
 */
public record SectionQuery(String id, String name, String topic) {}
