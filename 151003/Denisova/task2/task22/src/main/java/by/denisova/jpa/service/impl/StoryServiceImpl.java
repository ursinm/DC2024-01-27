package by.denisova.jpa.service.impl;

import by.denisova.jpa.exception.EntityNotFoundException;
import by.denisova.jpa.model.Story;
import by.denisova.jpa.repository.impl.StoryRepository;
import by.denisova.jpa.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryServiceImpl implements StoryService {

    public static final String TWEET_NOT_FOUND_MESSAGE = "Story with id '%d' doesn't exist";
    private final StoryRepository storyRepository;

    @Override
    public Story findById(Long id) {
        return storyRepository.findById(id).orElseThrow(() -> {
            final String message = TWEET_NOT_FOUND_MESSAGE.formatted(id);
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public void deleteById(Long id) {
        storyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(TWEET_NOT_FOUND_MESSAGE));

        storyRepository.deleteById(id);
    }

    @Override
    public Story save(Story story) {
        return storyRepository.save(story);
    }

    @Override
    public Story update(Story story) {
        storyRepository.findById(story.getId()).orElseThrow(()-> new EntityNotFoundException(TWEET_NOT_FOUND_MESSAGE));

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }
}
