package by.bsuir.publisher.controller;

import by.bsuir.publisher.dto.request.CreatorRequestDto;
import by.bsuir.publisher.dto.response.CreatorResponseDto;
import by.bsuir.publisher.service.CreatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping
    public ResponseEntity<List<CreatorResponseDto>> getAll() {
        return ResponseEntity.ok(creatorService.getAll());
    }

    @PostMapping
    public ResponseEntity<CreatorResponseDto> create(@Valid @RequestBody CreatorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(creatorService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatorResponseDto> get(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(creatorService.getById(id));
    }

    @PutMapping
    public ResponseEntity<CreatorResponseDto> update(@Valid @RequestBody CreatorRequestDto dto) {
        return ResponseEntity.ok(creatorService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        creatorService.deleteById(id);
    }
}
