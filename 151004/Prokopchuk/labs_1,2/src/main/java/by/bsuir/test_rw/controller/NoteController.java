package by.bsuir.test_rw.controller;

import by.bsuir.test_rw.exception.model.not_found.EntityNotFoundException;
import by.bsuir.test_rw.model.dto.note.NoteRequestTO;
import by.bsuir.test_rw.model.dto.note.NoteResponseTO;
import by.bsuir.test_rw.model.entity.implementations.Note;
import by.bsuir.test_rw.repository.implementations.in_memory.NoteInMemoryRepo;
import by.bsuir.test_rw.service.db_interaction.interfaces.NoteService;
import by.bsuir.test_rw.service.dto_convert.interfaces.NoteToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final NoteToConverter converter;

    @PostMapping()
    public ResponseEntity<NoteResponseTO> createNote(@RequestBody @Valid NoteRequestTO noteRequestTO) {
        Note message = converter.convertToEntity(noteRequestTO);
        noteService.save(message);
        NoteResponseTO noteResponseTO = converter.convertToDto(message);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteResponseTO);
    }

    @GetMapping()
    public ResponseEntity<List<NoteResponseTO>> receiveAllNotes() {
        List<Note> notes = noteService.findAll();
        List<NoteResponseTO> responseList = notes.stream()
                .map(converter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseTO> receiveNoteById(@PathVariable Long id) {
        Note note = noteService.findById(id);
        NoteResponseTO noteResponseTO = converter.convertToDto(note);
        return ResponseEntity.ok(noteResponseTO);
    }

    @PutMapping()
    public ResponseEntity<NoteResponseTO> updateNote(@RequestBody @Valid NoteRequestTO noteRequestTO) {
        Note note = converter.convertToEntity(noteRequestTO);
        noteService.save(note);
        NoteResponseTO noteResponseTO = converter.convertToDto(note);
        return ResponseEntity.ok(noteResponseTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        try {
            noteService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
