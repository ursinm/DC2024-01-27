package com.example.lab1.service;

import com.example.lab1.dao.NoteDao;
import com.example.lab1.dto.NoteRequestTo;
import com.example.lab1.dto.NoteResponseTo;
import com.example.lab1.exception.NotFoundException;
import com.example.lab1.mapper.NoteListMapper;
import com.example.lab1.mapper.NoteMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class NoteService {
    @Autowired
    NoteDao noteDao;
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    NoteListMapper noteListMapper;

    public NoteResponseTo create(@Valid NoteRequestTo noteRequestTo){
        return noteMapper.noteToNoteResponse(noteDao.create(noteMapper.noteRequestToNote(noteRequestTo)));
    }
    public NoteResponseTo read(@Min(0) int id) throws NotFoundException {
        NoteResponseTo noteResponseTo = noteMapper.noteToNoteResponse(noteDao.read(id));
        if(noteResponseTo != null)
            return noteResponseTo;
        else
            throw new NotFoundException("Note not found", 404);
    }
    public List<NoteResponseTo> readAll(){
        return noteListMapper.toNoteResponseList(noteDao.readAll());
    }
    public NoteResponseTo update(@Valid NoteRequestTo noteRequestTo, @Min(0) int id) throws NotFoundException {
        NoteResponseTo noteResponseTo = noteMapper.noteToNoteResponse(noteDao.update(noteMapper.noteRequestToNote(noteRequestTo),id));
        if(noteResponseTo != null)
            return noteResponseTo;
        else
            throw new NotFoundException("Note not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        boolean isDeleted = noteDao.delete(id);
        if(isDeleted)
            return true;
        else
            throw new NotFoundException("Note not found", 404);
    }

}
