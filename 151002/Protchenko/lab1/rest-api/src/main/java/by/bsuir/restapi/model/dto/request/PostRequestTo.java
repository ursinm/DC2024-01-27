package by.bsuir.restapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequestTo(
        Long id,

        @NotNull
        Long issueId,

        @NotBlank
        @Size(min = 2, max = 2048)
        String content
) {
}
