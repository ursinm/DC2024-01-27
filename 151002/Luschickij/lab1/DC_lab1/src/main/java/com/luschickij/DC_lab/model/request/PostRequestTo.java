package com.luschickij.DC_lab.model.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PostRequestTo(
        Long id,
        Long newsId,
        @NotBlank
        @Length(min = 2, max = 2048)
        String content
) {
}
