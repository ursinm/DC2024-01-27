package by.bsuir.services;

import by.bsuir.dao.EditorDao;
import by.bsuir.dto.EditorResponseTo;
import by.bsuir.mapper.EditorListMapper;
import by.bsuir.mapper.EditorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorService {
    @Autowired
    EditorMapper editorMapper;
    @Autowired
    EditorDao editorDao;
    @Autowired
    EditorListMapper editorListMapper;

    public EditorResponseTo getEditorById(Long id) {
        return editorMapper.editorToEditorResponse(editorDao.getEditorById(id));
    }

    public List<EditorResponseTo> getEditors() {
        return editorListMapper.toEditorResponseList(editorDao.getEditors());
    }

}
