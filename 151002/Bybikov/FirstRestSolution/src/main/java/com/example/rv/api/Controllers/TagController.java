package com.example.rv.api.Controllers;

import com.example.rv.impl.tag.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<TagResponseTo> getTags() {
        return tagService.tagMapper.tagToResponseTo(tagService.tagCrudRepository.getAll());
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    TagResponseTo makeTag(@RequestBody TagRequestTo tagRequestTo) {

        var toBack = tagService.tagCrudRepository.save(
                tagService.tagMapper.dtoToEntity(tagRequestTo)
        );

        Tag tag = toBack.orElse(null);

        assert tag != null;
        return tagService.tagMapper.tagToResponseTo(tag);
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.GET)
    TagResponseTo getTag(@PathVariable Long id) {
        return tagService.tagMapper.tagToResponseTo(
                Objects.requireNonNull(tagService.tagCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    TagResponseTo updateTag(@RequestBody TagRequestTo tagRequestTo, HttpServletResponse response) {
        Tag tag = tagService.tagMapper.dtoToEntity(tagRequestTo);
        var newTag = tagService.tagCrudRepository.update(tag).orElse(null);
        if (newTag != null) {
            response.setStatus(200);
            return tagService.tagMapper.tagToResponseTo(newTag);
        } else {
            response.setStatus(403);
            return tagService.tagMapper.tagToResponseTo(tag);
        }
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    int deleteTag(@PathVariable Long id, HttpServletResponse response) {
        Tag tagToDelete = tagService.tagCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(tagToDelete)) {
            response.setStatus(403);
        } else {
            tagService.tagCrudRepository.delete(tagToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
