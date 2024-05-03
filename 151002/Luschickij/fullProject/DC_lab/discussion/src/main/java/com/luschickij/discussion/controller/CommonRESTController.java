package com.luschickij.discussion.controller;

import com.luschickij.discussion.dto.post.PostRequestTo;
import com.luschickij.discussion.dto.post.PostResponseTo;
import com.luschickij.discussion.service.CommonRestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class CommonRESTController {

    CommonRestService service;
    Function<PostResponseTo, EntityModel<PostResponseTo>> assembler;

    public CommonRESTController(CommonRestService service,
                                Function<PostResponseTo, EntityModel<PostResponseTo>> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseTo> all() {
        return service.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> newEntity(@RequestBody PostRequestTo request) {
        Optional<PostResponseTo> response = service.create(request);

        EntityModel<PostResponseTo> entityModel = assembler.apply(response.orElse(null));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> one(@PathVariable("id") Long id) {
        EntityModel<PostResponseTo> entityModel = assembler.apply(service.one(id).orElse(null));

        return ResponseEntity
                .ok()
                .location(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public PostResponseTo updateEntity(
            @RequestBody PostRequestTo request) {
        Long id = request.getId();

        Optional<PostResponseTo> creator = service.update(id, request);

        return creator.orElse(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable("id") Long id) {

        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
