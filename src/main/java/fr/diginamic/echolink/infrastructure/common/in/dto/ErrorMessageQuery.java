package fr.diginamic.echolink.infrastructure.common.in.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Data Transfer Object representing an error response.
 *
 * @param message general error message describing the failure
 * @param fieldErrors list of field validation errors associated with the request
 */
@Schema(description = "Standard API error response")
public record ErrorMessageQuery(

        @Schema(description = "Human-readable error message", example = "Location not found")
        String message,

        @ArraySchema(
                schema = @Schema(implementation = FieldErrorQuery.class),
                arraySchema = @Schema(description = "List of errors associated with request fields")
        )
        List<FieldErrorQuery> fieldErrors
) {
}
