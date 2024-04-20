package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.dto.request.AuthorRequestDto;
import by.bsuir.publisher.model.dto.response.AuthorResponseDto;
import by.bsuir.publisher.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> get(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(authorService.get(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> create(@Valid @RequestBody AuthorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(dto));
    }

    @PutMapping
    public ResponseEntity<AuthorResponseDto> update(@Valid @RequestBody AuthorRequestDto dto) {
        return ResponseEntity.ok(authorService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable Long id) {
        authorService.delete(id);
    }

}
