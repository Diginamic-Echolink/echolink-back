package fr.diginamic.echolink.infrastructure.common.in.dto;

/**
 * Data Transfer Object representing a simple message response.
 *
 * @param message message returned to the client
 */
public record MessageQuery(String message) {}
