package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.dto.request.MarkerRequestDto;
import by.bsuir.publisher.model.dto.response.MarkerResponseDto;
import by.bsuir.publisher.service.MarkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/markers")
@RequiredArgsConstructor
public class MarkerController {

    private final MarkerService postService;

    @GetMapping
    public ResponseEntity<List<MarkerResponseDto>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkerResponseDto> get(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @PostMapping
    public ResponseEntity<MarkerResponseDto> create(@Valid @RequestBody MarkerRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(dto));
    }

    @PutMapping
    public ResponseEntity<MarkerResponseDto> update(@Valid @RequestBody MarkerRequestDto dto) {
        return ResponseEntity.ok(postService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable Long id) {
        postService.delete(id);
    }
}
