package com.luschickij.publisher.utils.modelassembler;

import com.luschickij.publisher.controller.crud.LabelController;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LabelModelAssembler implements RepresentationModelAssembler<LabelResponseTo, EntityModel<LabelResponseTo>> {
    @Override
    public EntityModel<LabelResponseTo> toModel(LabelResponseTo entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(LabelController.class).one(entity.getId(), null, null)).withSelfRel(),
                linkTo(methodOn(LabelController.class).all(null, null)).withRel("labels"));
    }
}
