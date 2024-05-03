package com.luschickij.publisher.utils.modelassembler;

import com.luschickij.publisher.controller.crud.CreatorController;
import com.luschickij.publisher.dto.creator.CreatorResponseTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CreatorModelAssembler implements RepresentationModelAssembler<CreatorResponseTo, EntityModel<CreatorResponseTo>> {

    @Override
    public EntityModel<CreatorResponseTo> toModel(CreatorResponseTo employee) {

        return EntityModel.of(employee, //
                linkTo(methodOn(CreatorController.class).one(employee.getId(), null, null)).withSelfRel(),
                linkTo(methodOn(CreatorController.class).all(null, null)).withRel("creators"));
    }
}
