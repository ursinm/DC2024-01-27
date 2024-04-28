package by.denisova.rest.service.impl;

import by.denisova.rest.model.Comment;
import by.denisova.rest.model.Story;
import by.denisova.rest.service.StoryService;
import by.denisova.rest.exception.EntityNotFoundException;
import by.denisova.rest.repository.AbstractRepository;
import by.denisova.rest.util.entity.CommentTestBuilder;
import by.denisova.rest.util.entity.StoryTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private StoryService storyService;

    @Mock
    private AbstractRepository<Comment, Long> commentRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Comment expected = CommentTestBuilder.comment().build();
            final Story story = StoryTestBuilder.story().build();

            doReturn(story)
                    .when(storyService)
                    .findById(expected.getStoryId());

            doReturn(expected)
                    .when(commentRepository)
                    .save(expected);

            final Comment actual = commentService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long commentId = 1L;
            final String expectedMessage = CommentServiceImpl.COMMENT_NOT_FOUND_MESSAGE.formatted(commentId);

            Mockito.doThrow(new EntityNotFoundException(expectedMessage))
                    .when(commentRepository)
                    .findById(commentId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> commentService.findById(commentId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Comment expected = CommentTestBuilder.comment().build();

            doReturn(Optional.of(expected))
                    .when(commentRepository)
                    .findById(expected.getId());

            final Comment actual = commentService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Comment comment = CommentTestBuilder.comment().build();
            final List<Comment> commentList = List.of(comment);

            doReturn(commentList)
                    .when(commentRepository)
                    .findAll();

            final List<Comment> actual = commentService.findAll();

            assertThat(actual).contains(comment);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long commentId = 1L;
            final String expectedMessage = CommentServiceImpl.COMMENT_NOT_FOUND_MESSAGE.formatted(commentId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(commentRepository)
                    .findById(commentId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> commentService.deleteById(commentId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long commentId = 1L;
            final Comment comment = CommentTestBuilder.comment().build();

            doReturn(Optional.of(comment))
                    .when(commentRepository)
                    .findById(commentId);

            doNothing().when(commentRepository)
                    .deleteById(commentId);

            commentService.deleteById(commentId);

            verify(commentRepository, times(1)).deleteById(commentId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Comment comment = CommentTestBuilder.comment().build();
            final String expectedMessage = CommentServiceImpl.COMMENT_NOT_FOUND_MESSAGE.formatted(comment.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(commentRepository)
                    .findById(comment.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> commentService.update(comment));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Comment expected = CommentTestBuilder.comment().build();
            final Story story = StoryTestBuilder.story().build();

            doReturn(story)
                    .when(storyService)
                    .findById(expected.getStoryId());

            doReturn(Optional.of(expected))
                    .when(commentRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(commentRepository)
                    .update(expected);

            final Comment actual = commentService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }
}