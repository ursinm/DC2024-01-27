package com.example.rw.controller;

import com.example.rw.exception.model.not_found.EntityNotFoundException;
import com.example.rw.model.dto.sticker.StickerRequestTo;
import com.example.rw.model.dto.sticker.StickerResponseTo;
import com.example.rw.model.entity.implementations.Sticker;
import com.example.rw.service.db_operations.interfaces.StickerService;
import com.example.rw.service.dto_converter.interfaces.StickerToConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.request.prefix}/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final StickerService stickerService;
    private final StickerToConverter stickerToConverter;

    @PostMapping()
    public ResponseEntity<StickerResponseTo> createSticker(@RequestBody @Valid StickerRequestTo stickerRequestTo) {
        Sticker sticker = stickerToConverter.convertToEntity(stickerRequestTo);
        stickerService.save(sticker);
        StickerResponseTo stickerResponseTo = stickerToConverter.convertToDto(sticker);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stickerResponseTo);
    }

    @GetMapping()
    public ResponseEntity<List<StickerResponseTo>> receiveAllStickers() {
        List<Sticker> stickers = stickerService.findAll();
        List<StickerResponseTo> responseList = stickers.stream()
                .map(stickerToConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseTo> receiveStickerById(@PathVariable Long id) {
        Sticker sticker = stickerService.findById(id);
        StickerResponseTo stickerResponseTo = stickerToConverter.convertToDto(sticker);
        return ResponseEntity.ok(stickerResponseTo);
    }

    @PutMapping()
    public ResponseEntity<StickerResponseTo> updateSticker(@RequestBody @Valid StickerRequestTo stickerRequestTo) {
        Sticker sticker = stickerToConverter.convertToEntity(stickerRequestTo);
        stickerService.save(sticker);
        StickerResponseTo stickerResponseTo = stickerToConverter.convertToDto(sticker);
        return ResponseEntity.ok(stickerResponseTo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStickerById(@PathVariable Long id) {
        try {
            stickerService.deleteById(id);
        } catch (EntityNotFoundException entityNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
