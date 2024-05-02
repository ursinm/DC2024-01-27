package by.haritonenko.jpa.service.impl;

import by.haritonenko.jpa.service.StoryService;
import by.haritonenko.jpa.exception.EntityNotFoundException;
import by.haritonenko.jpa.model.Story;
import by.haritonenko.jpa.repository.impl.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryServiceImpl implements StoryService {

    public static final String STORY_NOT_FOUND_MESSAGE = "Story with id '%d' doesn't exist";
    private final StoryRepository storyRepository;

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
        return storyRepository.save(story);
    }

    @Override
    public Story update(Story story) {
        storyRepository.findById(story.getId()).orElseThrow(()-> new EntityNotFoundException(STORY_NOT_FOUND_MESSAGE));

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }
}
