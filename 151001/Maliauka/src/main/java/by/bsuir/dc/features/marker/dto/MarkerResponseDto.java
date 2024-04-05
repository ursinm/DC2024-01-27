package by.bsuir.dc.features.marker.dto;

import by.bsuir.dc.features.marker.Marker;

import java.io.Serializable;

/**
 * DTO for {@link Marker}
 */
public record MarkerResponseDto(String name) implements Serializable {
}