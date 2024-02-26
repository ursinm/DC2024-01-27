package by.bsuir.taskrest.mapper;

import by.bsuir.taskrest.dto.request.CreatorRequestTo;
import by.bsuir.taskrest.dto.response.CreatorResponseTo;
import by.bsuir.taskrest.entity.Creator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreatorMapper {

    @Mapping(target = "id", ignore = true)
    Creator toEntity(CreatorRequestTo request);
    CreatorResponseTo toResponseTo(Creator entity);
    Creator updateEntity(@MappingTarget Creator entity, CreatorRequestTo request);
}
