package by.bsuir.lab2.controller;

import by.bsuir.lab2.dto.request.StickerRequestDto;
import by.bsuir.lab2.dto.response.StickerResponseDto;
import by.bsuir.lab2.service.StickerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final StickerService stickerService;

    @GetMapping
    public ResponseEntity<List<StickerResponseDto>> getAll() {
        return ResponseEntity.ok(stickerService.getAll());
    }

    @PostMapping
    public ResponseEntity<StickerResponseDto> create(@Valid @RequestBody StickerRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stickerService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseDto> get(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(stickerService.getById(id));
    }

    @PutMapping
    public ResponseEntity<StickerResponseDto> update(@Valid @RequestBody StickerRequestDto dto) {
        return ResponseEntity.ok(stickerService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        stickerService.deleteById(id);
    }
}
