package by.denisova.jpa.facade;

import by.denisova.jpa.dto.request.CreateCommentDto;
import by.denisova.jpa.dto.request.UpdateCommentDto;
import by.denisova.jpa.dto.response.CommentResponseDto;
import by.denisova.jpa.mapper.CommentMapper;
import by.denisova.jpa.model.Comment;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.service.CommentService;
import by.denisova.jpa.service.StoryService;
import by.denisova.jpa.util.dto.comment.request.CreateCommentDtoTestBuilder;
import by.denisova.jpa.util.dto.comment.request.UpdateCommentDtoTestBuilder;
import by.denisova.jpa.util.dto.comment.response.CommentResponseDtoTestBuilder;
import by.denisova.jpa.util.entity.DetachedCommentTestBuilder;
import by.denisova.jpa.util.entity.CommentTestBuilder;
import by.denisova.jpa.util.entity.StoryTestBuilder;
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
    private CommentFacade noteFacade;

    @Mock
    private CommentService noteService;

    @Mock
    private StoryService storyService;

    @Mock
    private CommentMapper noteMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Comment persistedComment = CommentTestBuilder.note().build();
        final CommentResponseDto expected = CommentResponseDtoTestBuilder.note().build();

        doReturn(persistedComment)
                .when(noteService)
                .findById(persistedComment.getId());

        doReturn(expected)
                .when(noteMapper)
                .toCommentResponse(persistedComment);

        final CommentResponseDto actual = noteFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Comment persistedComment = CommentTestBuilder.note().build();
        final CommentResponseDto expectedComment = CommentResponseDtoTestBuilder.note().build();
        final List<CommentResponseDto> expected = List.of(expectedComment);

        doReturn(List.of(persistedComment))
                .when(noteService)
                .findAll();

        doReturn(expectedComment)
                .when(noteMapper)
                .toCommentResponse(persistedComment);

        final List<CommentResponseDto> actual = noteFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Comment detachedComment = DetachedCommentTestBuilder.note().build();
        final Story story = StoryTestBuilder.story().build();
        final CreateCommentDto request = CreateCommentDtoTestBuilder.note().build();
        final Comment persistedComment = CommentTestBuilder.note().build();
        final CommentResponseDto expected = CommentResponseDtoTestBuilder.note().build();

        doReturn(detachedComment)
                .when(noteMapper)
                .toComment(request);

        doReturn(persistedComment)
                .when(noteService)
                .save(detachedComment);

        doReturn(expected)
                .when(noteMapper)
                .toCommentResponse(persistedComment);

        doReturn(story)
                .when(storyService)
                .findById(request.getStoryId());

        final CommentResponseDto actual = noteFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateCommentDto request = UpdateCommentDtoTestBuilder.note().build();
        final Comment persistedComment = CommentTestBuilder.note().build();
        final Story story = StoryTestBuilder.story().build();
        final CommentResponseDto expected = CommentResponseDtoTestBuilder.note().build();

        doReturn(persistedComment)
                .when(noteService)
                .findById(persistedComment.getId());

        doReturn(persistedComment)
                .when(noteMapper)
                .toComment(request, persistedComment);

        doReturn(persistedComment)
                .when(noteService)
                .update(persistedComment);

        doReturn(expected)
                .when(noteMapper)
                .toCommentResponse(persistedComment);

        doReturn(story)
                .when(storyService)
                .findById(request.getStoryId());

        final CommentResponseDto actual = noteFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long noteId = 1L;

        doNothing().when(noteService)
                .deleteById(noteId);

        noteFacade.delete(noteId);

        verify(noteService, times(1)).deleteById(noteId);
    }

}
