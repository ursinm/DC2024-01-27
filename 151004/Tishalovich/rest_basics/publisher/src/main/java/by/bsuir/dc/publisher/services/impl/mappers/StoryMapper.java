package by.bsuir.dc.publisher.services.impl.mappers;


import by.bsuir.dc.publisher.entities.Story;
import by.bsuir.dc.publisher.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.StoryResponseTo;
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
