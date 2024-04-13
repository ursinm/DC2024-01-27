package by.bsuir.messageapp.service.mapper;

import by.bsuir.messageapp.model.entity.Story;
import by.bsuir.messageapp.model.request.StoryRequestTo;
import by.bsuir.messageapp.model.response.StoryResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    StoryResponseTo getResponse(Story story);
    List<StoryResponseTo> getListResponse(Iterable<Story> stories);
    Story getStory(StoryRequestTo storyRequestTo);
}
