package by.bsuir.publisher.controller;

import by.bsuir.publisher.dto.request.NoteRequestDto;
import by.bsuir.publisher.dto.response.NoteResponseDto;
import by.bsuir.publisher.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

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
    public ResponseEntity<NoteResponseDto> create(@Valid @RequestBody NoteRequestDto dto,
                                                  HttpServletRequest request) {
        final Locale locale = request.getLocale();
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(dto, locale));
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

