package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.IssueRequestDto;
import by.bsuir.restapi.model.dto.response.IssueResponseDto;
import by.bsuir.restapi.model.entity.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IssueMapper {


    IssueResponseDto toDto(Issue Issue);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Issue toEntity(IssueRequestDto IssueRequestDto);

    List<IssueResponseDto> toDto(List<Issue> Issues);
}
