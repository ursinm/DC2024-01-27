package by.bsuir.dc.rest_basics.services.impl.mappers;


import by.bsuir.dc.rest_basics.entities.Story;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.StoryResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StoryMapper {

    @Mapping(target = "author", expression = "java(new by.bsuir.dc.rest_basics.entities.Author())")
    @Mapping(target = "author.id", source = "authorId")
    Story requestToModel(StoryRequestTo requestTo);

    @Mapping(target = "authorId", source = "author.id")
    StoryResponseTo modelToResponse(Story model);

}
