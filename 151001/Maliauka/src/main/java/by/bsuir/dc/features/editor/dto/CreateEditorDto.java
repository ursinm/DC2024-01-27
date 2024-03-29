package by.bsuir.dc.features.editor.dto;

import by.bsuir.dc.features.editor.Editor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Editor}
 */
public record CreateEditorDto(
        @NotBlank @Size(min = 2, max = 64) String login,
        @NotBlank @Size(min = 8, max = 128) String password,
        @NotBlank @Size(min = 2, max = 64) String firstName,
        @NotBlank @Size(min = 2, max = 64) String lastName
) implements Serializable {}