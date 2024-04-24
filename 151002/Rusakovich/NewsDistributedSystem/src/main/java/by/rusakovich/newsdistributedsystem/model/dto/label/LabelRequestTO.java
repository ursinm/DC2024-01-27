package by.rusakovich.newsdistributedsystem.model.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record LabelRequestTO (
    Long id,
    @NotBlank
    @Size(min = 2, max = 32)
    String name
){}
