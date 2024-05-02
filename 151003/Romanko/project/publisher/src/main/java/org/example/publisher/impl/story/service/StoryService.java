package org.example.publisher.impl.story.service;

import org.example.publisher.api.exception.DuplicateEntityException;
import org.example.publisher.api.exception.EntityNotFoundException;
import org.example.publisher.impl.user.User;
import org.example.publisher.impl.user.UserRepository;
import org.example.publisher.impl.story.Story;
import org.example.publisher.impl.story.StoryRepository;
import org.example.publisher.impl.story.dto.StoryRequestTo;
import org.example.publisher.impl.story.dto.StoryResponseTo;
import org.example.publisher.impl.story.mapper.Impl.StoryMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "storyCache")
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final StoryMapperImpl storyMapper;

    private final String ENTITY_NAME = "story";

    @Cacheable(cacheNames = "story")
    public List<StoryResponseTo> getStorys() {
        List<Story> storys = storyRepository.findAll();
        List<StoryResponseTo> storyResponseTos = new ArrayList<>();

        for (var item : storys) {
            storyResponseTos.add(storyMapper.storyToResponseTo(item));
        }
        return storyResponseTos;
    }

    @Cacheable(cacheNames = "story", key = "#id", unless = "#result == null")
    public StoryResponseTo getStoryById(BigInteger id) throws EntityNotFoundException {
        Optional<Story> story = storyRepository.findById(id);
        if (story.isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        return storyMapper.storyToResponseTo(story.get());
    }

    @CacheEvict(cacheNames = "story", allEntries = true)
    public StoryResponseTo saveStory(StoryRequestTo storyRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        Optional<User> user = userRepository.findById(storyRequestTo.getUserId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", storyRequestTo.getUserId());
        }

        if (storyRequestTo.getCreated() == null) {
            storyRequestTo.setCreated(new Date());
        }
        if (storyRequestTo.getModified() == null) {
            storyRequestTo.setModified(new Date());
        }
        if (isNumeric(storyRequestTo.getContent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "st_content should be a string, not a number.");
        }
        Story storyEntity = storyMapper.dtoToEntity(storyRequestTo, user.get());

        try {
            Story savedStory = storyRepository.save(storyEntity);
            return storyMapper.storyToResponseTo(savedStory);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "st_content");
        }
    }

    @CacheEvict(cacheNames = "story", allEntries = true)
    public StoryResponseTo updateStory(StoryRequestTo storyRequestTo) throws EntityNotFoundException, DuplicateEntityException {
        if (storyRepository.findById(storyRequestTo.getId()).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, storyRequestTo.getId());
        }

        Optional<User> user = userRepository.findById(storyRequestTo.getUserId());

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", storyRequestTo.getUserId());
        }
        if (storyRequestTo.getCreated() == null) {
            storyRequestTo.setCreated(new Date());
        }
        if (storyRequestTo.getModified() == null) {
            storyRequestTo.setModified(new Date());
        }

        Story story = storyMapper.dtoToEntity(storyRequestTo, user.get());
        try {
            Story savedStory = storyRepository.save(story);
            return storyMapper.storyToResponseTo(savedStory);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException(ENTITY_NAME, "story_content");
        }
    }

    @Caching(evict = { @CacheEvict(cacheNames = "story", key = "#id"),
            @CacheEvict(cacheNames = "story", allEntries = true) })
    public void deleteStory(BigInteger id) throws EntityNotFoundException {
        if (storyRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_NAME, id);
        }
        storyRepository.deleteById(id);
    }
}
