package by.bsuir.services;

import by.bsuir.dto.EditorRequestTo;
import by.bsuir.dto.EditorResponseTo;
import by.bsuir.entities.Editor;
import by.bsuir.mapper.EditorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class EditorService {
    @Autowired
    EditorMapper editorMapper;

    public EditorResponseTo editorResponseMapping(Editor editor){
        return editorMapper.editorToEditorResponse(editor);
    }

    public Editor editorRequestMapping(EditorRequestTo editor){
        return editorMapper.editorRequestToEditor(editor);
    }

}
