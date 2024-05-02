package application.services.impl;

import application.dto.StoryRequestTo;
import application.dto.StoryResponseTo;
import application.entites.Story;
import application.exceptions.DeleteException;
import application.exceptions.DuplicationException;
import application.exceptions.NotFoundException;
import application.exceptions.UpdateException;
import application.mappers.StoryListMapper;
import application.mappers.StoryMapper;
import application.repository.AuthorRepository;
import application.repository.StoryRepository;
import application.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImplStoryService implements StoryService {

    @Autowired
    StoryMapper storyMapper;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    StoryListMapper storyListMapper;

    @Override
    @Cacheable(value = "storys", key = "#id", unless = "#result == null")
    public StoryResponseTo getById(Long id) throws NotFoundException {
        Optional<Story> story = storyRepository.findById(id);
        return story.map(value -> storyMapper.toStoryResponse(value)).orElseThrow(() -> new NotFoundException("Story not found", 40004L));
    }

    @Override
    @Cacheable(cacheNames = "storys")
    public List<StoryResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Story> stories = storyRepository.findAll(pageable);
        return storyListMapper.toStoryResponseList(stories.toList());
    }

    @Override
    @CacheEvict(cacheNames = "storys", allEntries = true)
    public StoryResponseTo save(@Valid StoryRequestTo requestTo) {
        Story storyToSave = storyMapper.toStory(requestTo);
        if (storyRepository.existsByTitle(storyToSave.getTitle())) {
            throw new DuplicationException("Title duplication", 40005L);
        }
        if (requestTo.getAuthorId() != null) {
            storyToSave.setAuthor(authorRepository.findById(requestTo.getAuthorId()).orElseThrow(() -> new NotFoundException("User not found!", 40004L)));
        }
        return storyMapper.toStoryResponse(storyRepository.save(storyToSave));
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = "storys", key = "#id"),
            @CacheEvict(cacheNames = "storys", allEntries = true) })
    public void delete(Long id) throws DeleteException {
        if (!storyRepository.existsById(id)) {
            throw new DeleteException("Story not found!", 40004L);
        } else {
            storyRepository.deleteById(id);
        }
    }

    @Override
    @CacheEvict(cacheNames = "storys", allEntries = true)
    public StoryResponseTo update(@Valid StoryRequestTo requestTo) throws UpdateException {
        Story storyToUpdate = storyMapper.toStory(requestTo);
        if (!storyRepository.existsById(requestTo.getId())) {
            throw new UpdateException("Story not found!", 40004L);
        } else {
            if (requestTo.getAuthorId() != null) {
                storyToUpdate.setAuthor(authorRepository.findById(requestTo.getAuthorId()).orElseThrow(() -> new NotFoundException("User not found!", 40004L)));
            }
            return storyMapper.toStoryResponse(storyRepository.save(storyToUpdate));
        }
    }


    @Override
    public List<StoryResponseTo> getByData(List<String> markerName, List<Long> markerId, String authorLogin, String title, String content) {
        return storyListMapper.toStoryResponseList(storyRepository.findStoriesByParams(markerName, markerId, authorLogin, title, content));
    }
}
