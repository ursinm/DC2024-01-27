package com.example.lab1.controller;

import com.example.lab1.dto.NoteRequestTo;
import com.example.lab1.dto.NoteResponseTo;
import com.example.lab1.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/")
public class NoteController {
    @Autowired
    NoteService noteService;

    @PostMapping(value = "notes")
    public ResponseEntity<?> create(@RequestBody NoteRequestTo noteRequestTo) {
        NoteResponseTo note = noteService.create(noteRequestTo);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @GetMapping(value = "notes", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read() {
        final List<NoteResponseTo> list = noteService.readAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "notes/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        NoteResponseTo note = noteService.read(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PutMapping(value = "notes")
    public ResponseEntity<?> update(@RequestBody NoteRequestTo noteRequestTo) {
        NoteResponseTo note = noteService.update(noteRequestTo, noteRequestTo.getId());
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping(value = "notes/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = noteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
