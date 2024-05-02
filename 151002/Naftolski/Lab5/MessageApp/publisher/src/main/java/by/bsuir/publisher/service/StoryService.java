package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.repository.StoryRepository;
import by.bsuir.publisher.model.entity.Story;
import by.bsuir.publisher.model.request.StoryRequestTo;
import by.bsuir.publisher.model.response.StoryResponseTo;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.exceptions.ResourceStateException;
import by.bsuir.publisher.service.mapper.StoryMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@CacheConfig(cacheNames = "storyCache")
public class StoryService implements IService<StoryRequestTo, StoryResponseTo> {
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    @Cacheable(cacheNames = "storys", key = "#id", unless = "#result == null")
    @Override
    public StoryResponseTo findById(Long id) {
        return storyRepository.findById(id).map(storyMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Cacheable(cacheNames = "storys")
    @Override
    public List<StoryResponseTo> findAll() {
        return storyMapper.getListResponse(storyRepository.findAll());
    }

    @CacheEvict(cacheNames = "storys", allEntries = true)
    @Override
    public StoryResponseTo create(StoryRequestTo request) {
        StoryResponseTo response = storyMapper.getResponse(storyRepository.save(storyMapper.getStory(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @CacheEvict(cacheNames = "storys", allEntries = true)
    @Override
    public StoryResponseTo update(StoryRequestTo request) {
        Story story = storyMapper.getStory(request);
        story = storyRepository.save(story);
        StoryResponseTo response = storyMapper.getResponse(story);

//        StoryResponseTo response = storyMapper.getResponse(storyRepository.save(storyMapper.getStory(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @CacheEvict(cacheNames = "storys", key = "#id")
    @Override
    public void removeById(Long id) {
        Story story = storyRepository.findById(id).orElseThrow(StoryService::removeException);

        storyRepository.delete(story);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 41, "Can't find story by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 42, "Can't create story");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 43, "Can't update story");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 44, "Can't remove story");
    }
}
