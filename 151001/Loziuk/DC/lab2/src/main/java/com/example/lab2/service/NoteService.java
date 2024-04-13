package com.example.lab2.service;

import com.example.lab2.dto.NoteRequestTo;
import com.example.lab2.dto.NoteResponseTo;
import com.example.lab2.exception.NotFoundException;
import com.example.lab2.mapper.NoteListMapper;
import com.example.lab2.mapper.NoteMapper;
import com.example.lab2.model.Note;
import com.example.lab2.repository.IssueRepository;
import com.example.lab2.repository.NoteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class NoteService {
    @Autowired
    //NoteDao noteDao;
    NoteRepository noteRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    NoteListMapper noteListMapper;

    public NoteResponseTo create(@Valid NoteRequestTo noteRequestTo){
        Note note = noteMapper.noteRequestToNote(noteRequestTo);
        if(noteRequestTo.getIssueId() != 0){
            note.setIssue(issueRepository.findById(noteRequestTo.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found", 404)));
        }
        return noteMapper.noteToNoteResponse(noteRepository.save(note));
    }

    public List<NoteResponseTo> readAll(int pageInd, int numOfElem,String sortedBy,String direction){
        Pageable p;
        if(direction != null && direction.equals("asc"))
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).ascending());
        else
            p = PageRequest.of(pageInd, numOfElem, Sort.by(sortedBy).descending());

        Page<Note> res = noteRepository.findAll(p);
        return noteListMapper.toNoteResponseList(res.toList());
    }

    public NoteResponseTo read(@Min(0) int id) throws NotFoundException {
        if(noteRepository.existsById(id)){
            NoteResponseTo noteResponseTo = noteMapper.noteToNoteResponse(noteRepository.getReferenceById(id));
            return noteResponseTo;
        }
        else
            throw new NotFoundException("Note not found", 404);
    }
    public NoteResponseTo update(@Valid NoteRequestTo noteRequestTo, @Min(0) int id) throws NotFoundException {
        if(noteRepository.existsById(id)){
            Note note = noteMapper.noteRequestToNote(noteRequestTo);
            note.setId(id);
            if(noteRequestTo.getIssueId() != 0){
                note.setIssue(issueRepository.findById(noteRequestTo.getIssueId()).orElseThrow(() -> new NotFoundException("Issue not found", 404)));
            }
            return noteMapper.noteToNoteResponse(noteRepository.save(note));
        }
        else
            throw new NotFoundException("Note not found", 404);
    }
    public boolean delete(@Min(0) int id) throws NotFoundException {
        if(noteRepository.existsById(id)){
            noteRepository.deleteById(id);
            return true;
        }
        else
            throw new NotFoundException("Note not found", 404);
    }

}
