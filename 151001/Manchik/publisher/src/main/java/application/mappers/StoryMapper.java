package application.mappers;

import application.dto.StoryRequestTo;
import application.dto.StoryResponseTo;
import application.entites.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story toStory(StoryRequestTo story);
    @Mapping(target = "authorId", source = "story.author.id")
    StoryResponseTo toStoryResponse(Story story);
}
