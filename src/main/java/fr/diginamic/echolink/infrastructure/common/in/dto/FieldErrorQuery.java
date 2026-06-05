package fr.diginamic.echolink.infrastructure.common.in.dto;

/**
 * Data Transfer Object representing a validation error
 * associated with a specific request field.
 *
 * @param fieldName name of the field containing the error
 * @param message validation error message
 */
public record FieldErrorQuery(String fieldName, String message) {}
