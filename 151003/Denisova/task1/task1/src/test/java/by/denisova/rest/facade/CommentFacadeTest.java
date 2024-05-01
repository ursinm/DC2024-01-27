package by.denisova.rest.facade;

import by.denisova.rest.dto.request.CreateCommentDto;
import by.denisova.rest.dto.request.UpdateCommentDto;
import by.denisova.rest.dto.response.CommentResponseDto;
import by.denisova.rest.mapper.CommentMapper;
import by.denisova.rest.model.Comment;
import by.denisova.rest.service.CommentService;
import by.denisova.rest.util.dto.request.CreateCommentDtoTestBuilder;
import by.denisova.rest.util.dto.request.UpdateCommentDtoTestBuilder;
import by.denisova.rest.util.entity.DetachedCommentTestBuilder;
import by.denisova.rest.util.entity.CommentTestBuilder;
import by.denisova.rest.util.dto.response.CommentResponseDtoTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommentFacadeTest {

    @InjectMocks
    private CommentFacade commentFacade;

    @Mock
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Comment persistedComment = CommentTestBuilder.comment().build();
        final CommentResponseDto expected = CommentResponseDtoTestBuilder.comment().build();

        doReturn(persistedComment)
                .when(commentService)
                .findById(persistedComment.getId());

        doReturn(expected)
                .when(commentMapper)
                .toCommentResponse(persistedComment);

        final CommentResponseDto actual = commentFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Comment persistedComment = CommentTestBuilder.comment().build();
        final CommentResponseDto expectedComment = CommentResponseDtoTestBuilder.comment().build();
        final List<CommentResponseDto> expected = List.of(expectedComment);

        doReturn(List.of(persistedComment))
                .when(commentService)
                .findAll();

        doReturn(expectedComment)
                .when(commentMapper)
                .toCommentResponse(persistedComment);

        final List<CommentResponseDto> actual = commentFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Comment detachedComment = DetachedCommentTestBuilder.comment().build();
        final CreateCommentDto request = CreateCommentDtoTestBuilder.comment().build();
        final Comment persistedComment = CommentTestBuilder.comment().build();
        final CommentResponseDto expected = CommentResponseDtoTestBuilder.comment().build();

        doReturn(detachedComment)
                .when(commentMapper)
                .toComment(request);

        doReturn(persistedComment)
                .when(commentService)
                .save(detachedComment);

        doReturn(expected)
                .when(commentMapper)
                .toCommentResponse(persistedComment);

        final CommentResponseDto actual = commentFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateCommentDto request = UpdateCommentDtoTestBuilder.comment().build();
        final Comment persistedComment = CommentTestBuilder.comment().build();
        final CommentResponseDto expected = CommentResponseDtoTestBuilder.comment().build();

        doReturn(persistedComment)
                .when(commentService)
                .findById(persistedComment.getId());

        doReturn(persistedComment)
                .when(commentMapper)
                .toComment(request, persistedComment);

        doReturn(persistedComment)
                .when(commentService)
                .update(persistedComment);

        doReturn(expected)
                .when(commentMapper)
                .toCommentResponse(persistedComment);

        final CommentResponseDto actual = commentFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long commentId = 1L;

        doNothing().when(commentService)
                .deleteById(commentId);

        commentFacade.delete(commentId);

        verify(commentService, times(1)).deleteById(commentId);
    }

}
