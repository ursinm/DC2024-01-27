package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.IssueRequestTo;
import by.bsuir.restapi.model.dto.response.IssueResponseTo;
import by.bsuir.restapi.model.entity.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IssueMapper {


    IssueResponseTo toDto(Issue Issue);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Issue toEntity(IssueRequestTo IssueRequestTo);
}
