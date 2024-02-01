package by.bsuir.poit.dc.rest.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import lombok.Builder;

/**
 * @author Paval Shlyk
 * @since 01/02/2024
 */
@Builder
public record ErrorDto(
    int errorCode,
    String errorMessage,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable String[] errors
) {
}
