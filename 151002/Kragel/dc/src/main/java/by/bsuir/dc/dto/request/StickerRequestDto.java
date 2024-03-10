package by.bsuir.dc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StickerRequestDto(
        Long id,

        @NotBlank
        @Size(min = 2, max = 32)
        String name
) {
}
