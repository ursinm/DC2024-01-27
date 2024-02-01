package com.poluectov.rvlab1.utils.modelassembler;

import com.poluectov.rvlab1.controller.IssueController;
import com.poluectov.rvlab1.controller.MarkerController;
import com.poluectov.rvlab1.controller.UserController;
import com.poluectov.rvlab1.dto.issue.IssueResponseTo;
import com.poluectov.rvlab1.dto.marker.MarkerResponseTo;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IssueModelAssembler implements RepresentationModelAssembler<IssueResponseTo, EntityModel<IssueResponseTo>> {
    @Override
    @NonNull
    public EntityModel<IssueResponseTo> toModel(@NonNull IssueResponseTo entity) {
            EntityModel<IssueResponseTo> model = EntityModel.of(entity,
                linkTo(methodOn(IssueController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).one(entity.getUser().getId())).withRel("users"),
                linkTo(methodOn(IssueController.class).all()).withRel("issues"));

        if (entity.getMarkers() != null) {
            for (MarkerResponseTo marker : entity.getMarkers()) {
                model.add(linkTo(methodOn(MarkerController.class).one(marker.getId())).withRel("markers"));
            }
        }

        return model;
    }
}
