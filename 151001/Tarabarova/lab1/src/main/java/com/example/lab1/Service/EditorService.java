package com.example.lab1.Service;

import com.example.lab1.DAO.EditorDao;
import com.example.lab1.DTO.EditorRequestTo;
import com.example.lab1.DTO.EditorResponseTo;
import com.example.lab1.Exception.NotFoundException;
import com.example.lab1.Mapper.EditorListMapper;
import com.example.lab1.Mapper.EditorMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class EditorService {

    @Autowired
    EditorMapper editorMapper;
    @Autowired
    EditorDao editorDao;
    @Autowired
    EditorListMapper editorListMapper;

    public EditorResponseTo create(@Valid EditorRequestTo editor){
        return editorMapper.editorToEditorResponse(editorDao.create(editorMapper.editorRequestToEditor(editor)));
    }
    public List<EditorResponseTo> readAll(){
        return editorListMapper.toEditorResponseList(editorDao.readAll());
    }
    public EditorResponseTo read(@Min(0) int id) throws NotFoundException {
        EditorResponseTo cr = editorMapper.editorToEditorResponse(editorDao.read(id));
        if(cr != null)
            return cr;
        else
            throw new NotFoundException("Editor not found", 404);
    }
    public EditorResponseTo update(@Valid EditorRequestTo editor, @Min(0) int id){
        EditorResponseTo cr = editorMapper.editorToEditorResponse(editorDao.update(editorMapper.editorRequestToEditor(editor),id));
        if(cr != null)
            return cr;
        else
            throw new NotFoundException("Editor not found", 404);
    }
    public boolean delete(@Min(0) int id){
        if(editorDao.delete(id))
            return true;
        else
            throw new NotFoundException("Editor not found", 404);
    }


}
