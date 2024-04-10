package by.bsuir.dc.features.editor;

import by.bsuir.dc.features.editor.dto.CreateEditorDto;
import by.bsuir.dc.features.editor.dto.EditorResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface EditorMapper {
    EditorResponseDto toDto(Editor editor);

    Editor toEntity(CreateEditorDto editorResponseDto);

    List<EditorResponseDto> toDtoList(List<Editor> list);
}
