package by.bsuir.lab2.controller;

import by.bsuir.lab2.dto.request.NoteRequestDto;
import by.bsuir.lab2.dto.response.NoteResponseDto;
import by.bsuir.lab2.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAll() {
        return ResponseEntity.ok(noteService.getAll());
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(@Valid @RequestBody NoteRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> get(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @PutMapping
    public ResponseEntity<NoteResponseDto> update(@Valid @RequestBody NoteRequestDto dto) {
        return ResponseEntity.ok(noteService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        noteService.deleteById(id);
    }
}
