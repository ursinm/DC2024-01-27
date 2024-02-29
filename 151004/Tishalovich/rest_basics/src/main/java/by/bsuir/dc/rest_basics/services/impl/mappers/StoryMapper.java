package by.bsuir.dc.rest_basics.services.impl.mappers;


import by.bsuir.dc.rest_basics.entities.Story;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.StoryResponseTo;
import org.mapstruct.Mapper;

@Mapper
public interface StoryMapper extends EntityMapper<StoryRequestTo, StoryResponseTo, Story> {
}
