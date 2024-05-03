package by.haritonenko.rest.facade;

import by.haritonenko.rest.mapper.StoryMapper;
import by.haritonenko.rest.service.StoryService;
import by.haritonenko.rest.dto.request.CreateStoryDto;
import by.haritonenko.rest.dto.request.UpdateStoryDto;
import by.haritonenko.rest.dto.response.StoryResponseDto;
import by.haritonenko.rest.model.Story;
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
