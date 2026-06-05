package fr.diginamic.echolink.infrastructure.common.in.dto;

import java.util.List;

/**
 * Data Transfer Object representing an error response.
 *
 * @param message general error message describing the failure
 * @param fieldErrors list of field validation errors associated with the request
 */
public record ErrorMessageQuery(String message, List<FieldErrorQuery> fieldErrors) {}
