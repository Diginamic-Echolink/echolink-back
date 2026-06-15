package fr.diginamic.echolink.infrastructure.common.in.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Generic pagination response used by API endpoints.
 *
 * @param <T> type of content returned in the page
 */
@Schema(
        name = "PageResponse",
        description = "Generic paginated response wrapper"
)
public record PageResponse<T>(

        @Schema(description = "Content of the current page")
        List<T> content,

        @Schema(description = "Current page index (0-based)", example = "0")
        int page,

        @Schema(description = "Number of elements per page", example = "20")
        int size,

        @Schema(description = "Total number of elements across all pages", example = "152")
        long totalElements,

        @Schema(description = "Total number of pages available", example = "8")
        int totalPages,

        @Schema(description = "Indicates if this is the first page", example = "true")
        boolean first,

        @Schema(description = "Indicates if this is the last page", example = "false")
        boolean last
) {
}
