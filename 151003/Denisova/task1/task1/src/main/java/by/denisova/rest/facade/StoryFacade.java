package by.denisova.rest.facade;

import by.denisova.rest.mapper.StoryMapper;
import by.denisova.rest.model.Story;
import by.denisova.rest.service.StoryService;
import by.denisova.rest.dto.request.CreateStoryDto;
import by.denisova.rest.dto.request.UpdateStoryDto;
import by.denisova.rest.dto.response.StoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoryFacade {

    private final StoryService storyService;
    private final StoryMapper storyMapper;

    public StoryResponseDto findById(Long id) {
        Story story = storyService.findById(id);
        return storyMapper.toStoryResponse(story);
    }

    public List<StoryResponseDto> findAll() {
        List<Story> storys = storyService.findAll();

        return storys.stream().map(storyMapper::toStoryResponse).toList();
    }

    public StoryResponseDto save(CreateStoryDto storyRequest) {
        Story story = storyMapper.toStory(storyRequest);

        Story savedStory = storyService.save(story);

        return storyMapper.toStoryResponse(savedStory);
    }

    public StoryResponseDto update(UpdateStoryDto storyRequest) {
        Story story = storyService.findById(storyRequest.getId());

        Story updatedStory = storyMapper.toStory(storyRequest, story);

        return storyMapper.toStoryResponse(storyService.update(updatedStory));
    }

    public void delete(Long id) {
        storyService.deleteById(id);
    }
}
