package by.haritonenko.rest.service.impl;

import by.haritonenko.rest.exception.EntityNotFoundException;
import by.haritonenko.rest.repository.AbstractRepository;
import by.haritonenko.rest.model.Author;
import by.haritonenko.rest.model.Story;
import by.haritonenko.rest.service.AuthorService;
import by.haritonenko.rest.util.entity.AuthorTestBuilder;
import by.haritonenko.rest.util.entity.StoryTestBuilder;
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
class StoryServiceImplTest {

    @InjectMocks
    private StoryServiceImpl storyService;

    @Mock
    private AuthorService authorService;

    @Mock
    private AbstractRepository<Story, Long> storyRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Story expected = StoryTestBuilder.story().build();
            final Author author = AuthorTestBuilder.author().build();

            doReturn(author)
                    .when(authorService)
                    .findById(author.getId());

            doReturn(expected)
                    .when(storyRepository)
                    .save(expected);

            final Story actual = storyService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long storyId = 1L;
            final String expectedMessage = StoryServiceImpl.STORY_NOT_FOUND_MESSAGE.formatted(storyId);

            Mockito.doThrow(new EntityNotFoundException(expectedMessage))
                    .when(storyRepository)
                    .findById(storyId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> storyService.findById(storyId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Story expected = StoryTestBuilder.story().build();

            doReturn(Optional.of(expected))
                    .when(storyRepository)
                    .findById(expected.getId());

            final Story actual = storyService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Story story = StoryTestBuilder.story().build();
            final List<Story> storyList = List.of(story);

            doReturn(storyList)
                    .when(storyRepository)
                    .findAll();

            final List<Story> actual = storyService.findAll();

            assertThat(actual).contains(story);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long storyId = 1L;
            final String expectedMessage = StoryServiceImpl.STORY_NOT_FOUND_MESSAGE.formatted(storyId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(storyRepository)
                    .findById(storyId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> storyService.deleteById(storyId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long storyId = 1L;
            final Story story = StoryTestBuilder.story().build();

            doReturn(Optional.of(story))
                    .when(storyRepository)
                    .findById(storyId);

            doNothing().when(storyRepository)
                    .deleteById(storyId);

            storyService.deleteById(storyId);

            verify(storyRepository, times(1)).deleteById(storyId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Story story = StoryTestBuilder.story().build();
            final String expectedMessage = StoryServiceImpl.STORY_NOT_FOUND_MESSAGE.formatted(story.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(storyRepository)
                    .findById(story.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> storyService.update(story));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Story expected = StoryTestBuilder.story().build();
            final Author author = AuthorTestBuilder.author().build();

            doReturn(author)
                    .when(authorService)
                    .findById(author.getId());

            doReturn(Optional.of(expected))
                    .when(storyRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(storyRepository)
                    .update(expected);

            final Story actual = storyService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

}
