package com.luschickij.publisher.utils.modelassembler;

import com.luschickij.publisher.controller.crud.NewsController;
import com.luschickij.publisher.controller.crud.LabelController;
import com.luschickij.publisher.controller.crud.CreatorController;
import com.luschickij.publisher.dto.news.NewsResponseTo;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NewsModelAssembler implements RepresentationModelAssembler<NewsResponseTo, EntityModel<NewsResponseTo>> {
    @Override
    public EntityModel<NewsResponseTo> toModel(NewsResponseTo entity) {
        EntityModel<NewsResponseTo> model = EntityModel.of(entity,
                linkTo(methodOn(NewsController.class).one(entity.getId(), null, null)).withSelfRel(),
                linkTo(methodOn(CreatorController.class).one(entity.getCreatorId(), null, null)).withRel("creators"),
                linkTo(methodOn(NewsController.class).all(null, null)).withRel("newss"));

        if (entity.getLabels() != null) {
            for (Long label : entity.getLabels()) {
                model.add(linkTo(methodOn(LabelController.class).one(label, null, null)).withRel("labels"));
            }
        }

        return model;
    }
}
