package services.tweetservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import services.tweetservice.domain.request.StickerRequestTo;
import services.tweetservice.domain.response.StickerResponseTo;
import services.tweetservice.serivces.StickerService;

import java.util.List;

@RestController
@RequestMapping("/stickers")
public class StickerController {
    private final StickerService stickerService;

    @Autowired
    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }

    @PostMapping
    public ResponseEntity<StickerResponseTo> createMessage(@RequestBody StickerRequestTo stickerRequestTo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stickerService.create(stickerRequestTo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerResponseTo> findMessageById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.findStickerById(id));
    }

    @GetMapping
    public ResponseEntity<List<StickerResponseTo>> findAllMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.read());
    }

    @PutMapping
    public ResponseEntity<StickerResponseTo> updateMessage(@RequestBody StickerRequestTo stickerRequestTo) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.update(stickerRequestTo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMessageById(@PathVariable Long id) {
        stickerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }
}
