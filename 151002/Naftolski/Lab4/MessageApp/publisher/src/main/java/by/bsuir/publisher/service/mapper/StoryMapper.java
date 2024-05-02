package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Story;
import by.bsuir.publisher.model.request.StoryRequestTo;
import by.bsuir.publisher.model.response.StoryResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface StoryMapper {
    @Mapping(source = "creator.id", target = "creatorId")
    StoryResponseTo getResponse(Story story);
    List<StoryResponseTo> getListResponse(Iterable<Story> stories);
    @Mapping(source = "creatorId", target = "creator", qualifiedByName = "creatorRefFromCreatorId")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Story getStory(StoryRequestTo storyRequestTo);
}
