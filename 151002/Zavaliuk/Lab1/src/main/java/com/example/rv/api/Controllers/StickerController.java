package com.example.rv.api.Controllers;

import com.example.rv.impl.tag.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class StickerController {
    private final StickerService stickerService;

    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }


    @RequestMapping(value = "/stickers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<StickerResponseTo> getTags() {
        return stickerService.tagMapper.tagToResponseTo(stickerService.tagCrudRepository.getAll());
    }

    @RequestMapping(value = "/stickers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    StickerResponseTo makeTag(@RequestBody StickerRequestTo stickerRequestTo) {

        var toBack = stickerService.tagCrudRepository.save(
                stickerService.tagMapper.dtoToEntity(stickerRequestTo)
        );

        Sticker sticker = toBack.orElse(null);

        assert sticker != null;
        return stickerService.tagMapper.tagToResponseTo(sticker);
    }

    @RequestMapping(value = "/stickers/{id}", method = RequestMethod.GET)
    StickerResponseTo getTag(@PathVariable Long id) {
        return stickerService.tagMapper.tagToResponseTo(
                Objects.requireNonNull(stickerService.tagCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/stickers", method = RequestMethod.PUT)
    StickerResponseTo updateTag(@RequestBody StickerRequestTo stickerRequestTo, HttpServletResponse response) {
        Sticker sticker = stickerService.tagMapper.dtoToEntity(stickerRequestTo);
        var newTag = stickerService.tagCrudRepository.update(sticker).orElse(null);
        if (newTag != null) {
            response.setStatus(200);
            return stickerService.tagMapper.tagToResponseTo(newTag);
        } else {
            response.setStatus(403);
            return stickerService.tagMapper.tagToResponseTo(sticker);
        }
    }

    @RequestMapping(value = "/stickers/{id}", method = RequestMethod.DELETE)
    int deleteTag(@PathVariable Long id, HttpServletResponse response) {
        Sticker stickerToDelete = stickerService.tagCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(stickerToDelete)) {
            response.setStatus(403);
        } else {
            stickerService.tagCrudRepository.delete(stickerToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
