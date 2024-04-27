package by.denisova.jpa.facade;

import by.denisova.jpa.dto.request.CreateStoryDto;
import by.denisova.jpa.dto.request.UpdateStoryDto;
import by.denisova.jpa.dto.response.StoryResponseDto;
import by.denisova.jpa.mapper.StoryMapper;
import by.denisova.jpa.model.Editor;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.service.EditorService;
import by.denisova.jpa.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoryFacade {

    private final StoryService storyService;
    private final EditorService editorService;
    private final StoryMapper storyMapper;

    @Transactional(readOnly = true)
    public StoryResponseDto findById(Long id) {
        Story story = storyService.findById(id);
        return storyMapper.toStoryResponse(story);
    }

    @Transactional(readOnly = true)
    public List<StoryResponseDto> findAll() {
        List<Story> storys = storyService.findAll();

        return storys.stream().map(storyMapper::toStoryResponse).toList();
    }

    @Transactional
    public StoryResponseDto save(CreateStoryDto storyRequest) {
        Story story = storyMapper.toStory(storyRequest);

        Editor editor = editorService.findById(storyRequest.getEditorId());
        story.setEditor(editor);

        Story savedStory = storyService.save(story);

        return storyMapper.toStoryResponse(savedStory);
    }

    @Transactional
    public StoryResponseDto update(UpdateStoryDto storyRequest) {
        Story story = storyService.findById(storyRequest.getId());

        Story updatedStory = storyMapper.toStory(storyRequest, story);

        if (storyRequest.getEditorId() != null) {
            Editor editor = editorService.findById(storyRequest.getEditorId());
            updatedStory.setEditor(editor);
        }

        return storyMapper.toStoryResponse(storyService.update(updatedStory));
    }

    @Transactional
    public void delete(Long id) {
        storyService.deleteById(id);
    }
}
