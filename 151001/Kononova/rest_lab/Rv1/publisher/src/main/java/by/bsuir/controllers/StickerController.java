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
    public ResponseEntity<List<StickerResponseTo>> getStickers(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(StickerService.getStickers(pageNumber, pageSize, sortBy, sortOrder));
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
    public ResponseEntity<List<StickerResponseTo>> getUserByIssueId(@PathVariable Long id){
        return ResponseEntity.status(SUCCESS_CODE).body(StickerService.getStickerByIssueId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable Long id) {
        StickerService.deleteSticker(id);
        return ResponseEntity.noContent().build();
    }
}