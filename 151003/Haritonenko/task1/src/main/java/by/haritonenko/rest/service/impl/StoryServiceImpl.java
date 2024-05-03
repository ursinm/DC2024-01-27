package by.haritonenko.rest.service.impl;

import by.haritonenko.rest.exception.EntityNotFoundException;
import by.haritonenko.rest.repository.AbstractRepository;
import by.haritonenko.rest.service.StoryService;
import by.haritonenko.rest.model.Story;
import by.haritonenko.rest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryServiceImpl implements StoryService {

    public static final String STORY_NOT_FOUND_MESSAGE = "Story with id '%d' doesn't exist";
    private final AbstractRepository<Story, Long> storyRepository;
    private final AuthorService authorService;

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
        authorService.findById(story.getAuthorId());
        return storyRepository.save(story);
    }

    @Override
    public Story update(Story story) {
        storyRepository.findById(story.getId()).orElseThrow(()-> new EntityNotFoundException(STORY_NOT_FOUND_MESSAGE));

        authorService.findById(story.getAuthorId());
        return storyRepository.update(story);
    }

    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }
}
