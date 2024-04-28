package by.bsuir.restapi.model.mapper;

import by.bsuir.restapi.model.dto.request.IssueRequestDto;
import by.bsuir.restapi.model.dto.response.IssueResponseDto;
import by.bsuir.restapi.model.entity.Issue;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CustomMapper.class)
public interface IssueMapper {

    @Mapping(target = "authorId", source = "author.id")
    IssueResponseDto toDto(Issue Issue);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "author", source = "authorId", qualifiedByName = "authorIdToAuthorRef")
    Issue toEntity(IssueRequestDto IssueRequestDto);

    List<IssueResponseDto> toDto(List<Issue> Issues);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "authorId", qualifiedByName = "authorIdToAuthorRef")
    Issue partialUpdate(IssueRequestDto tweetRequestDto, @MappingTarget Issue issue);
}
