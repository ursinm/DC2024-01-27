package by.bsuir.poit.dc.rest.api.dto.request;

import lombok.Builder;

/**
 * @author Paval Shlyk
 * @since 01/02/2024
 */
@Builder
public record ErrorDto(
    int errorCode,
    String errorMessage
) {
}
