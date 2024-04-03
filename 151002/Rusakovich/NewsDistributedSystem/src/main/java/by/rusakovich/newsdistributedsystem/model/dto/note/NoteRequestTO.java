package by.rusakovich.newsdistributedsystem.model.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoteRequestTO(Long id, Long newsId, @NotBlank @Size(min = 2, max = 2048) String content) {
}
