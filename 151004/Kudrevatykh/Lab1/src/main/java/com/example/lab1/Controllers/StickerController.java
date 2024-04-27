package com.example.lab1.Controllers;

import com.example.lab1.DTO.StickerRequestTo;
import com.example.lab1.DTO.StickerResponseTo;
import com.example.lab1.Service.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1.0/")
public class StickerController {
    @Autowired
    StickerService stickerService;

    @PostMapping(value = "stickers")
    public ResponseEntity<?> create(@RequestBody StickerRequestTo stickerRequestTo) {
        StickerResponseTo sticker = stickerService.create(stickerRequestTo);
        return new ResponseEntity<>(sticker, HttpStatus.CREATED);
    }

    @GetMapping(value = "stickers", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> read() {
        final List<StickerResponseTo> list = stickerService.readAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "stickers/{id}")
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        StickerResponseTo sticker = stickerService.read(id);
        return new ResponseEntity<>(sticker, HttpStatus.OK);
    }

    @PutMapping(value = "stickers")
    public ResponseEntity<?> update(@RequestBody StickerRequestTo stickerRequestTo) {
        StickerResponseTo sticker = stickerService.update(stickerRequestTo, stickerRequestTo.getId());
        return new ResponseEntity<>(sticker, HttpStatus.OK);
    }

    @DeleteMapping(value = "stickers/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        boolean isDeleted = stickerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
