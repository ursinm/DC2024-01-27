package by.bsuir.controllers;

import by.bsuir.dto.StickerRequestTo;
import by.bsuir.dto.StickerResponseTo;
import by.bsuir.services.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/stickers")
public class StickerController {

    @Autowired
    StickerService StickerService;

    private static final int SUCCESS_CODE = 200;

    @GetMapping
    public ResponseEntity<List<StickerResponseTo>> getStickers() {
        return ResponseEntity.status(SUCCESS_CODE).body(StickerService.getStickers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseTo> getSticker(@PathVariable Long id) {
        return ResponseEntity.status(SUCCESS_CODE).body(StickerService.getStickerById(id));
    }

    @PostMapping
    public ResponseEntity<StickerResponseTo> saveSticker(@RequestBody StickerRequestTo Sticker) {
        StickerResponseTo savedSticker = StickerService.saveSticker(Sticker);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSticker);
    }

    @PutMapping()
    public ResponseEntity<StickerResponseTo> updateSticker(@RequestBody StickerRequestTo Sticker) {
        return ResponseEntity.status(HttpStatus.OK).body(StickerService.updateSticker(Sticker));
    }

    @GetMapping("/byIssue/{id}")
    public ResponseEntity<StickerResponseTo> getUserByIssueId(@PathVariable Long id){
        return ResponseEntity.status(SUCCESS_CODE).body(StickerService.getStickerByIssueId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable Long id) {
        StickerService.deleteSticker(id);
        return ResponseEntity.noContent().build();
    }
}