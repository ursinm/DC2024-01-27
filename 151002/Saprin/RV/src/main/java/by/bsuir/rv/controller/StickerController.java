package by.bsuir.rv.controller;

import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.service.sticker.IStickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/stickers")
public class StickerController {

    private final IStickerService stickerService;

    @Autowired
    public StickerController(IStickerService stickerService) {
        this.stickerService = stickerService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<StickerResponseTo> getStickers() {
        return stickerService.getStickers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo getStickerById(@PathVariable BigInteger id) throws EntityNotFoundException {
        return stickerService.getStickerById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StickerResponseTo createSticker(@RequestBody StickerRequestTo sticker) {
        return stickerService.createSticker(sticker);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo updateSticker(@RequestBody StickerRequestTo sticker) throws EntityNotFoundException {
        return stickerService.updateSticker(sticker);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSticker(@PathVariable BigInteger id) throws EntityNotFoundException {
        stickerService.deleteSticker(id);
    }

}
