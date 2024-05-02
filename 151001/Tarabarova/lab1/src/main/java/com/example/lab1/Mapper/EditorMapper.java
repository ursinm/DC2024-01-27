package com.example.lab1.Mapper;

import com.example.lab1.DTO.EditorRequestTo;
import com.example.lab1.DTO.EditorResponseTo;
import com.example.lab1.Model.Editor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditorMapper {
    Editor editorRequestToEditor(EditorRequestTo editorRequestTo);

    EditorResponseTo editorToEditorResponse(Editor editor);
}
