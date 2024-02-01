package com.poluectov.rvlab1.controller;

import com.poluectov.rvlab1.model.IdentifiedEntity;
import com.poluectov.rvlab1.service.CommonRestService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class CommonRESTController<Entity extends IdentifiedEntity,
        Request extends IdentifiedEntity,
        Response extends IdentifiedEntity> {

    CommonRestService<Entity, Request, Response> service;
    Function<Response, EntityModel<Response>> assembler;

    public CommonRESTController(CommonRestService<Entity, Request, Response> service,
                                Function<Response, EntityModel<Response>> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        List<EntityModel<Response>> entityModels = service.all().stream()
                .map(assembler)
                .toList();

        CollectionModel<EntityModel<Response>> collectionModel = CollectionModel.of(entityModels,
                linkTo(methodOn(this.getClass()).all()).withSelfRel());
        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);
    }

    @PostMapping
    public ResponseEntity<?> newEntity(@RequestBody Request request) {
        Response response = service.create(request);

        EntityModel<Response> entityModel =  assembler.apply(response);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable("id") BigInteger id) {
        EntityModel<Response> entityModel = assembler.apply(service.one(id));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable("id") BigInteger id,
                                        @RequestBody Request request) {

        Response user = service.update(id, request);

        EntityModel<Response> entityModel = assembler.apply(user);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntity(@PathVariable("id") BigInteger id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
