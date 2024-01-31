package by.bsuir.poit.dc.rest.api.dto.response;

import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.Note}
 */
@Builder
public record NoteDto(
    Long id,
    String content
) implements Serializable {
}