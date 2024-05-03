package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.entity.Story;
import by.bsuir.publisher.model.request.StoryRequestTo;
import by.bsuir.publisher.model.response.StoryResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface StoryMapper {
    @Mapping(target = "editorId", source = "editor.id")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "modified", source = "modified")
    StoryResponseTo getResponseTo(Story story);

    List<StoryResponseTo> getListResponseTo(Iterable<Story> news);

    @Mapping(target = "editor", source = "editorId", qualifiedByName = "editorIdToEditorRef")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Story getNews(StoryRequestTo storyRequestTo);
}
