package org.example.dc.services;

import org.example.dc.model.Editor;
import org.example.dc.model.EditorDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EditorService {
    List<EditorDto> getEditors();
    EditorDto getEditorById(int id);
    EditorDto createEditor(EditorDto editorDto);
    boolean delete(int id) throws Exception;
    EditorDto updateEditor(EditorDto editorDto);
}
