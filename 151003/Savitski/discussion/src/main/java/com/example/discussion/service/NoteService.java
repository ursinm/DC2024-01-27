package com.example.discussion.service;


import com.example.discussion.exceptions.DeleteException;
import com.example.discussion.exceptions.NotFoundException;
import com.example.discussion.exceptions.UpdateException;
import com.example.discussion.mapper.NoteMapperDisc;
import com.example.discussion.mapper.NoteMapperOwn;
import com.example.discussion.model.entity.Note;
import com.example.discussion.model.request.NoteRequestTo;
import com.example.discussion.model.response.NoteResponseTo;
import com.example.discussion.repository.NoteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapperOwn noteMapperDisc;

    public NoteResponseTo getNoteById(Long id) throws NotFoundException {
        Note comment = noteRepository.findById(id).orElseThrow(
                () -> new NotFoundException(404L, "Comment not found!"));
        return noteMapperDisc.entityToResponse(comment);
    }

    public List<NoteResponseTo> getNotes() {
        return toNoteResponseList(noteRepository.findAll());
    }

    public NoteResponseTo saveNote(@Valid NoteRequestTo comment, String country) {
        Note noteToSave = noteRequestToNote(comment);
        noteToSave.setId(getId());
        noteToSave.setCountry(getCountry(country));
        return noteToNoteResponse(noteRepository.save(noteToSave));
    }

    public void deleteNote(Long id) throws DeleteException {
        Optional<Note> note = noteRepository.findById(id).stream().findFirst();
        if (note.isEmpty()) {
            throw new DeleteException("Comment not found!", 404L);
        } else {
            noteRepository.deleteByCountryAndStoryIdAndId(note.get().getCountry(), note.get().getStoryId(), note.get().getId());
        }
    }

    public NoteResponseTo updateNote(@Valid NoteRequestTo note, String country) throws UpdateException {
        Note noteToUpdate = noteRequestToNote(note);
        noteToUpdate.setId(note.getId());
        noteToUpdate.setCountry(getCountry(country));
        Optional<Note> commentOptional = noteRepository.findById(note.getId()).stream().findFirst();
        if (commentOptional.isEmpty()) {
            throw new UpdateException("Comment not found!", 404L);
        } else {
            noteRepository.deleteByCountryAndStoryIdAndId(commentOptional.get().getCountry(), commentOptional.get().getStoryId(), commentOptional.get().getId());
            return noteToNoteResponse(noteRepository.save(noteToUpdate));
        }
    }

    /*public List<NoteResponseTo> getNoteByIssueId(Long issueId) throws NotFoundException {
        List<Note> notes = noteRepository.findByStoryId(issueId);
        if (notes.isEmpty()) {
            throw new NotFoundException(404L, "Comment not found!");
        }
        return toNoteResponseList(notes);
    }*/

    private NoteResponseTo noteToNoteResponse(Note note) {
        NoteResponseTo noteResponseTo = new NoteResponseTo();
        noteResponseTo.setStoryId(note.getStoryId());
        noteResponseTo.setId(note.getId());
        noteResponseTo.setContent(note.getContent());
        return noteResponseTo;
    }

    private List<NoteResponseTo> toNoteResponseList(List<Note> notes) {
        List<NoteResponseTo> response = new ArrayList<>();
        for (Note note : notes) {
            response.add(noteToNoteResponse(note));
        }
        return response;
    }

    private Note noteRequestToNote(NoteRequestTo requestTo) {
        Note note = new Note();
        note.setId(requestTo.getId());
        note.setContent(requestTo.getContent());
        note.setStoryId(requestTo.getStoryId());
        return note;
    }

    private long getId (){
        int currentSecond = (int) (System.currentTimeMillis() / 1000);

        int shiftedTime = currentSecond << 10;

        int randomBits = new Random().nextInt(1 << 10);

        return Math.abs(shiftedTime | randomBits);
    }

    private String getCountry(String requestHeader){
        Map<String, Double> languageMap = getStringDoubleMap(requestHeader);
        Map<String, Double> loadMap = new HashMap<>();
        for (String country: languageMap.keySet()){
            loadMap.put(country, noteRepository.countByCountry(country)*(1-languageMap.get(country)));
        }
        double minValue = Double.MAX_VALUE;
        String minKey = null;

        for (Map.Entry<String, Double> entry : loadMap.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                minKey = entry.getKey();
            }
        }
        return minKey;
    }

    /*private NoteResponseTo findByCountryAndId(NoteRequestTo noteRequestTo) {
        Note entity = noteMapper.requestToEntity(noteRequestTo);
        return noteMapper.entityToResponse(noteRepository.findByCountryAndId(entity.getCountry(), entity.getId()).orElseThrow());
    }*/

    private static Map<String, Double> getStringDoubleMap(String requestHeader) {
        String[] languages = requestHeader.split(",");
        Map<String, Double> languageMap = new HashMap<>();
        for (String language : languages) {
            String[] parts = language.split(";");
            String lang = parts[0].trim();
            double priority = 1.0; // По умолчанию
            if (parts.length > 1) {
                String[] priorityParts = parts[1].split("=");
                priority = Double.parseDouble(priorityParts[1]);
            }
            languageMap.put(lang, priority);
        }
        return languageMap;
    }
}
