package com.example.lab2.Mapper;

import com.example.lab2.DTO.EditorRequestTo;
import com.example.lab2.DTO.EditorResponseTo;
import com.example.lab2.Model.Editor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditorMapper {
    Editor editorRequestToEditor(EditorRequestTo editorRequestTo);

    EditorResponseTo editorToEditorResponse(Editor editor);
}
