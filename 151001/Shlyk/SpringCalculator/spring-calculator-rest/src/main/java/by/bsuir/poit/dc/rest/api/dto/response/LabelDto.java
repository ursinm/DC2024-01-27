package by.bsuir.poit.dc.rest.api.dto.response;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.Label}
 */
public record LabelDto(
    Long id,
    String name
) implements Serializable {
}