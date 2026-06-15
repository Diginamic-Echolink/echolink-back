package fr.diginamic.echolink.infrastructure.common.in.mapper;

import fr.diginamic.echolink.infrastructure.common.in.dto.PageResponse;
import org.springframework.data.domain.Page;

import java.util.function.Function;

/**
 * Utility mapper for converting Spring Page into API PageResponse.
 */
public final class PageResponseMapper {

    private PageResponseMapper() {}

    public static <T, U> PageResponse<U> toPageResponse(Page<T> page, Function<T, U> mapper) {

        return new PageResponse<>(
                page.getContent().stream().map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
