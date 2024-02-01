package com.poluectov.rvlab1.utils.modelassembler;

import com.poluectov.rvlab1.controller.MarkerController;
import com.poluectov.rvlab1.dto.marker.MarkerResponseTo;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class MarkerModelAssembler implements RepresentationModelAssembler<MarkerResponseTo, EntityModel<MarkerResponseTo>> {
    @Override
    @NonNull
    public EntityModel<MarkerResponseTo> toModel(@NonNull MarkerResponseTo entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(MarkerController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(MarkerController.class).all()).withRel("markers"));
    }
}
