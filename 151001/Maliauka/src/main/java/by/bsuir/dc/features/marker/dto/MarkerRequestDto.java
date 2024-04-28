package by.bsuir.dc.features.marker.dto;

import by.bsuir.dc.features.marker.Marker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Marker}
 */
public record MarkerRequestDto(
        @NotBlank @Size(min = 2, max = 32) String name
) implements Serializable {}