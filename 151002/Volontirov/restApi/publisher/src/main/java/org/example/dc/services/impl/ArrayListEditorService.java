package org.example.dc.services.impl;

import org.example.dc.model.Editor;
import org.example.dc.model.EditorDto;
import org.example.dc.services.EditorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ArrayListEditorService implements EditorService {
    private List<EditorDto> editors = new ArrayList();
    private static int id = 1;

    @Override
    public List<EditorDto> getEditors() {
        return editors;
    }

    @Override
    public EditorDto getEditorById(int id) {
        return editors.stream()
                .filter(editor -> editor.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public EditorDto createEditor(EditorDto editorDto) {
        editorDto.setId(id++);
        editors.add(editorDto);
        return editorDto;
    }

    @Override
    public boolean delete(int id) throws Exception {
        EditorDto editor = editors.stream()
                .filter(ed -> ed.getId() == id)
                .findFirst()
                .orElseThrow(Exception::new);
        editors.remove(editor);
        return true;
    }

    @Override
    public EditorDto updateEditor(EditorDto editorDto) {
        EditorDto editor = editors.stream()
                .filter(ed -> ed.getId() == editorDto.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        editor.setId(editorDto.getId());
        editor.setLastname(editorDto.getLastname());
        editor.setFirstname(editorDto.getFirstname());
        editor.setPassword(editorDto.getPassword());
        editor.setLogin(editorDto.getLogin());
        return editor;
    }
}
