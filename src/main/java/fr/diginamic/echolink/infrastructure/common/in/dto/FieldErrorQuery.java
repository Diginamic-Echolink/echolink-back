package fr.diginamic.echolink.infrastructure.common.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object representing a validation error
 * associated with a specific request field.
 *
 * @param fieldName name of the field containing the error
 * @param message validation error message
 */
@Schema(description = "Validation error associated with a specific field")
public record FieldErrorQuery(

        @Schema(description = "Field name", example = "postalCode")
        String fieldName,

        @Schema(description = "Validation error message", example = "must not be blank")
        String message
) {
}
