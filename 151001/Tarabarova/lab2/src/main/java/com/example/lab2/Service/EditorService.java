package com.example.lab2.Service;

import com.example.lab2.DTO.EditorRequestTo;
import com.example.lab2.DTO.EditorResponseTo;
import com.example.lab2.Exception.DuplicateException;
import com.example.lab2.Exception.NotFoundException;
import com.example.lab2.Mapper.EditorListMapper;
import com.example.lab2.Mapper.EditorMapper;
import com.example.lab2.Model.Editor;
import com.example.lab2.Repository.EditorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Validated
public class EditorService {

    @Autowired
    EditorMapper editorMapper;
    @Autowired
    EditorRepository editorRepository;
    @Autowired
    EditorListMapper editorListMapper;

    public EditorResponseTo create(@Valid EditorRequestTo editorRequestTo){
        Editor editor = editorMapper.editorRequestToEditor(editorRequestTo);
        if(editorRepository.existsByLogin(editor.getLogin())){
            throw new DuplicateException("Login duplication", 403);
        }
        return editorMapper.editorToEditorResponse(editorRepository.save(editor));
    }
    public List<EditorResponseTo> readAll(int pageInd, int numOfElem, String sortedBy, String direction){
        Pageable p;
        if(direction != null && direction.equals("asc")){
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).ascending());
        }
        else{
            p = PageRequest.of(pageInd,numOfElem, Sort.by(sortedBy).descending());
        }
        Page<Editor> res = editorRepository.findAll(p);
        return editorListMapper.toEditorResponseList(res.toList());
    }

    public EditorResponseTo read(@Min(0) int id) throws NotFoundException {
        if(editorRepository.existsById(id)){
            EditorResponseTo cr = editorMapper.editorToEditorResponse(editorRepository.getReferenceById(id));
            return cr;
        }
        else
            throw new NotFoundException("Editor not found", 404);
    }
    public EditorResponseTo update(@Valid EditorRequestTo editor, @Min(0) int id){
        if(editorRepository.existsById(id)){
            Editor cr =  editorMapper.editorRequestToEditor(editor);
            cr.setId(id);
            return editorMapper.editorToEditorResponse(editorRepository.save(cr));
        }
        else
            throw new NotFoundException("Editor not found", 404);
    }
    public boolean delete(@Min(0) int id){
        if(editorRepository.existsById(id)){
            editorRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Editor not found", 404);
    }


}
