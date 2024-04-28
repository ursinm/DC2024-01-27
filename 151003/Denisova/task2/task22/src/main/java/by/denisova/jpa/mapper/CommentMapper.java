package by.denisova.jpa.mapper;

import by.denisova.jpa.dto.request.CreateCommentDto;
import by.denisova.jpa.dto.request.UpdateCommentDto;
import by.denisova.jpa.dto.response.CommentResponseDto;
import by.denisova.jpa.model.Comment;
import org.mapstruct.*;

@Mapper
public interface CommentMapper {

    @Mapping(target = "story.id", source = "storyId")
    Comment toComment(CreateCommentDto commentRequest);

    @Mapping(target="storyId", source="story.id")
    CommentResponseDto toCommentResponse(Comment comment);

    @Mapping(target = "story.id", source = "storyId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment toComment(UpdateCommentDto commentRequest, @MappingTarget Comment comment);
}
