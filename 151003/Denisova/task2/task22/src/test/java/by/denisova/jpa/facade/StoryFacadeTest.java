package by.denisova.jpa.facade;

import by.denisova.jpa.dto.request.CreateStoryDto;
import by.denisova.jpa.dto.request.UpdateStoryDto;
import by.denisova.jpa.dto.response.StoryResponseDto;
import by.denisova.jpa.mapper.StoryMapper;
import by.denisova.jpa.model.Editor;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.service.EditorService;
import by.denisova.jpa.service.StoryService;
import by.denisova.jpa.util.dto.story.request.CreateStoryDtoTestBuilder;
import by.denisova.jpa.util.dto.story.request.UpdateStoryDtoTestBuilder;
import by.denisova.jpa.util.dto.story.response.StoryResponseDtoTestBuilder;
import by.denisova.jpa.util.entity.EditorTestBuilder;
import by.denisova.jpa.util.entity.DetachedStoryTestBuilder;
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
class StoryFacadeTest {

    @InjectMocks
    private StoryFacade storyFacade;

    @Mock
    private StoryService storyService;

    @Mock
    private StoryMapper storyMapper;

    @Mock
    private EditorService editorService;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Story persistedStory = StoryTestBuilder.story().build();
        final StoryResponseDto expected = StoryResponseDtoTestBuilder.story().build();

        doReturn(persistedStory)
                .when(storyService)
                .findById(persistedStory.getId());

        doReturn(expected)
                .when(storyMapper)
                .toStoryResponse(persistedStory);

        final StoryResponseDto actual = storyFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Story persistedStory = StoryTestBuilder.story().build();
        final StoryResponseDto expectedStory = StoryResponseDtoTestBuilder.story().build();
        final List<StoryResponseDto> expected = List.of(expectedStory);

        doReturn(List.of(persistedStory))
                .when(storyService)
                .findAll();

        doReturn(expectedStory)
                .when(storyMapper)
                .toStoryResponse(persistedStory);

        final List<StoryResponseDto> actual = storyFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Story detachedStory = DetachedStoryTestBuilder.story().build();
        final Editor editor = EditorTestBuilder.editor().build();
        final CreateStoryDto request = CreateStoryDtoTestBuilder.story().build();
        final Story persistedStory = StoryTestBuilder.story().build();
        final StoryResponseDto expected = StoryResponseDtoTestBuilder.story().build();

        doReturn(detachedStory)
                .when(storyMapper)
                .toStory(request);

        doReturn(persistedStory)
                .when(storyService)
                .save(detachedStory);

        doReturn(expected)
                .when(storyMapper)
                .toStoryResponse(persistedStory);

        doReturn(editor)
                .when(editorService)
                .findById(request.getEditorId());

        final StoryResponseDto actual = storyFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateStoryDto request = UpdateStoryDtoTestBuilder.story().build();
        final Story persistedStory = StoryTestBuilder.story().build();
        final Editor editor = EditorTestBuilder.editor().build();
        final StoryResponseDto expected = StoryResponseDtoTestBuilder.story().build();

        doReturn(persistedStory)
                .when(storyService)
                .findById(persistedStory.getId());

        doReturn(persistedStory)
                .when(storyMapper)
                .toStory(request, persistedStory);

        doReturn(persistedStory)
                .when(storyService)
                .update(persistedStory);

        doReturn(expected)
                .when(storyMapper)
                .toStoryResponse(persistedStory);

        doReturn(editor)
                .when(editorService)
                .findById(request.getEditorId());

        final StoryResponseDto actual = storyFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long storyId = 1L;

        doNothing().when(storyService)
                .deleteById(storyId);

        storyFacade.delete(storyId);

        verify(storyService, times(1)).deleteById(storyId);
    }
}
