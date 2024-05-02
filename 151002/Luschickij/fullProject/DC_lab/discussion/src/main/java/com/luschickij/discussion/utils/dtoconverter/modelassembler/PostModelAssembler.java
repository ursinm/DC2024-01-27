package com.luschickij.discussion.utils.dtoconverter.modelassembler;

import com.luschickij.discussion.controller.PostController;
import com.luschickij.discussion.dto.post.PostResponseTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler
        implements RepresentationModelAssembler<PostResponseTo, EntityModel<PostResponseTo>> {
    @Override
    public EntityModel<PostResponseTo> toModel(PostResponseTo entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PostController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).all()).withRel("posts"));
    }
}
