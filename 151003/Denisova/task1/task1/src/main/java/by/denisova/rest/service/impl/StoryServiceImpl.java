package by.denisova.rest.service.impl;

import by.denisova.rest.model.Story;
import by.denisova.rest.repository.AbstractRepository;
import by.denisova.rest.service.EditorService;
import by.denisova.rest.service.StoryService;
import by.denisova.rest.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryServiceImpl implements StoryService {

    public static final String STORY_NOT_FOUND_MESSAGE = "Story with id '%d' doesn't exist";
    private final AbstractRepository<Story, Long> storyRepository;
    private final EditorService editorService;

    @Override
    public Story findById(Long id) {
        return storyRepository.findById(id).orElseThrow(() -> {
            final String message = STORY_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        storyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(STORY_NOT_FOUND_MESSAGE));

        storyRepository.deleteById(id);
    }

    @Override
    public Story save(Story story) {
        editorService.findById(story.getEditorId());
        return storyRepository.save(story);
    }

    @Override
    public Story update(Story story) {
        storyRepository.findById(story.getId()).orElseThrow(()-> new EntityNotFoundException(STORY_NOT_FOUND_MESSAGE));

        editorService.findById(story.getEditorId());
        return storyRepository.update(story);
    }

    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }
}
