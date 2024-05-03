package org.example.dc.controllers;

import org.example.dc.model.Note;
import org.example.dc.model.NoteDto;
import org.example.dc.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1.0/notes")
public class NotesController {
    private Map<Integer, NoteDto> cache = new HashMap<>();

    @Autowired
    private NoteService noteService;

    @GetMapping
    public List<NoteDto> getNotes() {
        return noteService.getNotes();
    }

    @Cacheable
    @GetMapping("/{id}")
    public NoteDto getNote(@PathVariable int id) {
        if(!cache.containsKey(id)) {
            cache.put(id, noteService.getNoteById(id));
            return noteService.getNoteById(id);
        } else {
            return cache.get(id);
        }
    }

    @PostMapping
    public NoteDto createNote(@RequestBody @Valid NoteDto note, BindingResult br, HttpServletResponse response) {
        if(br.hasErrors()) {
            response.setStatus(403);
            return new NoteDto();
        }
        response.setStatus(201);
        try {
            return noteService.create(note);
        } catch (Exception e) {
            response.setStatus(403);
            return new NoteDto();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            noteService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    @PutMapping
    public NoteDto update(@RequestBody @Valid NoteDto note, BindingResult br, HttpServletResponse response) {
        cache.remove(note.getId());
        if(br.hasErrors()) {
            response.setStatus(402);
            return noteService.getNoteById(note.getId());
        }
        response.setStatus(200);
        return noteService.update(note);
    }
}
