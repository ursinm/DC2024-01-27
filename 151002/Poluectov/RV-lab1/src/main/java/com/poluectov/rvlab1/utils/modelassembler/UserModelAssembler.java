package com.poluectov.rvlab1.utils.modelassembler;

import com.poluectov.rvlab1.controller.UserController;
import com.poluectov.rvlab1.dto.user.UserResponseTo;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseTo, EntityModel<UserResponseTo>> {

    @Override
    @NonNull
    public EntityModel<UserResponseTo> toModel(@NonNull UserResponseTo employee) {

        return EntityModel.of(employee, //
                linkTo(methodOn(UserController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}
