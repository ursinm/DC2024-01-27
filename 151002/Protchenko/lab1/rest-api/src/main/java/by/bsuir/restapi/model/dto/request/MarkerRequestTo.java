package by.bsuir.restapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarkerRequestTo(
        Long id,

        @NotBlank
        @Size(min = 2, max = 32)
        String name
) {
}
