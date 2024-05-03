package com.example.rv.api.Controllers;

import com.example.rv.impl.label.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }


    @RequestMapping(value = "/labels", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<LabelResponseTo> getTags() {
        return labelService.tagMapper.tagToResponseTo(labelService.tagCrudRepository.getAll());
    }

    @RequestMapping(value = "/labels", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    LabelResponseTo makeTag(@RequestBody LabelRequestTo labelRequestTo) {

        var toBack = labelService.tagCrudRepository.save(
                labelService.tagMapper.dtoToEntity(labelRequestTo)
        );

        Label label = toBack.orElse(null);

        assert label != null;
        return labelService.tagMapper.tagToResponseTo(label);
    }

    @RequestMapping(value = "/labels/{id}", method = RequestMethod.GET)
    LabelResponseTo getTag(@PathVariable Long id) {
        return labelService.tagMapper.tagToResponseTo(
                Objects.requireNonNull(labelService.tagCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/labels", method = RequestMethod.PUT)
    LabelResponseTo updateTag(@RequestBody LabelRequestTo labelRequestTo, HttpServletResponse response) {
        Label label = labelService.tagMapper.dtoToEntity(labelRequestTo);
        var newTag = labelService.tagCrudRepository.update(label).orElse(null);
        if (newTag != null) {
            response.setStatus(200);
            return labelService.tagMapper.tagToResponseTo(newTag);
        } else {
            response.setStatus(403);
            return labelService.tagMapper.tagToResponseTo(label);
        }
    }

    @RequestMapping(value = "/labels/{id}", method = RequestMethod.DELETE)
    int deleteTag(@PathVariable Long id, HttpServletResponse response) {
        Label labelToDelete = labelService.tagCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(labelToDelete)) {
            response.setStatus(403);
        } else {
            labelService.tagCrudRepository.delete(labelToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
