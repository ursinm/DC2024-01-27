package com.luschickij.publisher.utils.modelassembler;

import com.luschickij.publisher.controller.crud.PostController;
import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.dto.post.PostResponseTo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<PostResponseTo, EntityModel<PostResponseTo>> {
    @Override
    public EntityModel<PostResponseTo> toModel(PostResponseTo entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PostController.class).one(entity.getId(), null, null)).withSelfRel(),
                linkTo(methodOn(PostController.class).all(null, null)).withRel("posts"));
    }
}
