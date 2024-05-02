package by.haritonenko.jpa.facade;

import by.haritonenko.jpa.dto.request.CreateStoryDto;
import by.haritonenko.jpa.dto.request.UpdateStoryDto;
import by.haritonenko.jpa.dto.response.StoryResponseDto;
import by.haritonenko.jpa.mapper.StoryMapper;
import by.haritonenko.jpa.model.Author;
import by.haritonenko.jpa.model.Story;
import by.haritonenko.jpa.service.AuthorService;
import by.haritonenko.jpa.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StoryFacade {

    private final StoryService storyService;
    private final AuthorService authorService;
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

        Author author = authorService.findById(storyRequest.getAuthorId());
        story.setAuthor(author);

        Story savedStory = storyService.save(story);

        return storyMapper.toStoryResponse(savedStory);
    }

    @Transactional
    public StoryResponseDto update(UpdateStoryDto storyRequest) {
        Story story = storyService.findById(storyRequest.getId());

        Story updatedStory = storyMapper.toStory(storyRequest, story);

        if (storyRequest.getAuthorId() != null) {
            Author author = authorService.findById(storyRequest.getAuthorId());
            updatedStory.setAuthor(author);
        }

        return storyMapper.toStoryResponse(storyService.update(updatedStory));
    }

    @Transactional
    public void delete(Long id) {
        storyService.deleteById(id);
    }
}
