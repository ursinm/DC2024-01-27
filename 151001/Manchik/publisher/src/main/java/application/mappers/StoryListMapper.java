package application.mappers;

import application.dto.StoryRequestTo;
import application.dto.StoryResponseTo;
import application.entites.Story;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = StoryMapper.class)
public interface StoryListMapper {
    List<Story> toStoryList(List<StoryRequestTo> story);
    List<StoryResponseTo> toStoryResponseList(List<Story> story);
}
