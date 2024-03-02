package by.bsuir.taskrest.mapper;

import by.bsuir.taskrest.dto.request.StoryRequestTo;
import by.bsuir.taskrest.dto.response.StoryResponseTo;
import by.bsuir.taskrest.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    Story toEntity(StoryRequestTo request);

    StoryResponseTo toResponseTo(Story entity);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", expression = "java(java.time.LocalDateTime.now())")
    Story updateEntity(@MappingTarget Story entity, StoryRequestTo request);
}
