package com.example.rv.api.Controllers;

import com.example.rv.impl.creator.Creator;
import com.example.rv.impl.creator.CreatorRequestTo;
import com.example.rv.impl.creator.CreatorResponseTo;
import com.example.rv.impl.creator.CreatorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1.0")
public class CreatorController {

    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }


    @RequestMapping(value = "/creators", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<CreatorResponseTo> getEditors() {
        return creatorService.editorMapper.editorToResponseTo(creatorService.editorCrudRepository.getAll());
    }

    @RequestMapping(value = "/creators", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    CreatorResponseTo makeEditor(@RequestBody CreatorRequestTo creatorRequestTo) {

        var toBack = creatorService.editorCrudRepository.save(
                creatorService.editorMapper.dtoToEntity(creatorRequestTo)
        );

        Creator creator = toBack.orElse(null);

        assert creator != null;
        return creatorService.editorMapper.editorToResponseTo(creator);
    }

    @RequestMapping(value = "/creators/{id}", method = RequestMethod.GET)
    CreatorResponseTo getEditor(@PathVariable Long id) {
        return creatorService.editorMapper.editorToResponseTo(
                Objects.requireNonNull(creatorService.editorCrudRepository.getById(id).orElse(null)));
    }

    @RequestMapping(value = "/creators", method = RequestMethod.PUT)
    CreatorResponseTo updateEditor(@RequestBody CreatorRequestTo creatorRequestTo, HttpServletResponse response) {
        Creator creator = creatorService.editorMapper.dtoToEntity(creatorRequestTo);
        var newEditor = creatorService.editorCrudRepository.update(creator).orElse(null);
        if (newEditor != null) {
            response.setStatus(200);
            return creatorService.editorMapper.editorToResponseTo(newEditor);
        } else{
            response.setStatus(403);
            return creatorService.editorMapper.editorToResponseTo(creator);
        }
    }

    @RequestMapping(value = "/creators/{id}", method = RequestMethod.DELETE)
    int deleteEditor(@PathVariable Long id, HttpServletResponse response) {
        Creator edToDelete = creatorService.editorCrudRepository.getById(id).orElse(null);
        if (Objects.isNull(edToDelete)) {
            response.setStatus(403);
        } else {
            creatorService.editorCrudRepository.delete(edToDelete);
            response.setStatus(204);
        }
        return 0;
    }
}
