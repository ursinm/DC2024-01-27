package by.bsuir.services;

import by.bsuir.dao.EditorDao;
import by.bsuir.dto.EditorRequestTo;
import by.bsuir.dto.EditorResponseTo;
import by.bsuir.entities.Editor;
import by.bsuir.mapper.EditorListMapper;
import by.bsuir.mapper.EditorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorService {
    @Autowired
    EditorMapper editorMapper;
    @Autowired
    EditorDao editorDao;
    @Autowired
    EditorListMapper editorListMapper;

    public EditorResponseTo getEditorById(Long id) {
        Optional<Editor> editor = editorDao.findById(id);
        return editor.map(value -> editorMapper.editorToEditorResponse(value)).orElse(null);
    }

    public List<EditorResponseTo> getEditors() {
        return editorListMapper.toEditorResponseList(editorDao.findAll());
    }

    public EditorResponseTo saveEditor(EditorRequestTo editor){
        Editor editorToSave = editorMapper.editorRequestToEditor(editor);
        return editorMapper.editorToEditorResponse(editorDao.save(editorToSave));
    }

    public void deleteEditor(Long id){
        editorDao.delete(id);
    }

    public EditorResponseTo updateEditor(EditorRequestTo editor, Long id){
        Editor editorToUpdate = editorMapper.editorRequestToEditor(editor);
        return editorMapper.editorToEditorResponse(editorDao.update(editorToUpdate, id));
    }

}
