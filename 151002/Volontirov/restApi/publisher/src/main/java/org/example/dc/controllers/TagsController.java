package org.example.dc.controllers;

import org.example.dc.model.TagDto;
import org.example.dc.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1.0/tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<TagDto> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{id}")
    public TagDto getTag(@PathVariable int id, HttpServletResponse response) {
        response.setStatus(200);
        return tagService.getTagById(id);
    }

    @PostMapping
    public TagDto createTag(@RequestBody @Valid TagDto tagDto, BindingResult br, HttpServletResponse response) {
        if (br.hasErrors()) {
            response.setStatus(400);
            return new TagDto();
        }
        response.setStatus(201);
        try {
            return tagService.createTag(tagDto);
        } catch (Exception e) {
            response.setStatus(403);
            return tagService.getTags().stream()
                    .filter(t -> t.getName().equals(tagDto.getName()))
                    .findFirst().orElse(new TagDto());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id, HttpServletResponse response) {
        try {
            response.setStatus(204);
            tagService.delete(id);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    @PutMapping
    public TagDto updateTag(@RequestBody @Valid TagDto tag, BindingResult br, HttpServletResponse response) {
        if(br.hasErrors()) {
            response.setStatus(402);
            return tagService.getTagById(tag.getId());
        }
        response.setStatus(200);
        return tagService.updateTag(tag);
    }
}
