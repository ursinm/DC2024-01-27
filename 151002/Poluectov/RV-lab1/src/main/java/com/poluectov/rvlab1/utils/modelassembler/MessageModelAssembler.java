package com.poluectov.rvlab1.utils.modelassembler;

import com.poluectov.rvlab1.controller.MessageController;
import com.poluectov.rvlab1.dto.message.MessageResponseTo;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MessageModelAssembler implements RepresentationModelAssembler<MessageResponseTo, EntityModel<MessageResponseTo>> {
    @Override
    @NonNull
    public EntityModel<MessageResponseTo> toModel(@NonNull MessageResponseTo entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(MessageController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(MessageController.class).all()).withRel("messages"));
    }
}
