package com.example.lab2.Controller;

import com.example.lab2.DTO.StickerRequestTo;
import com.example.lab2.DTO.StickerResponseTo;
import com.example.lab2.Service.StickerService;
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
    public ResponseEntity<?> read(
            @RequestParam(required = false, defaultValue = "0") Integer pageInd,
            @RequestParam(required = false, defaultValue = "20") Integer numOfElem,
            @RequestParam(required = false, defaultValue = "id") String sortedBy,
            @RequestParam(required = false, defaultValue = "desc") String direction)
    {
        final List<StickerResponseTo> list = stickerService.readAll(pageInd, numOfElem, sortedBy, direction);
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
