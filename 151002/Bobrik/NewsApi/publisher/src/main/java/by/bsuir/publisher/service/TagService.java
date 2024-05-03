package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.TagRepository;
import by.bsuir.publisher.model.entity.Tag;
import by.bsuir.publisher.model.request.TagRequestTo;
import by.bsuir.publisher.model.response.TagResponseTo;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.exceptions.ResourceStateException;
import by.bsuir.publisher.service.mapper.TagMapper;
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
@CacheConfig(cacheNames = "tagCache")
@RequiredArgsConstructor
public class TagService implements RestService <TagRequestTo, TagResponseTo> {
    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    @Cacheable(cacheNames = "tags")
    @Override
    public List<TagResponseTo> findAll() {
        return tagMapper.getListResponseTo(tagRepository.findAll());
    }

    @Cacheable(cacheNames = "tags", key = "#id", unless = "#result == null")
    @Override
    public TagResponseTo findById(Long id) {
        return tagMapper.getResponseTo(tagRepository
                .findById(id)
                .orElseThrow(() -> tagNotFoundException(id)));
    }

    @CacheEvict(cacheNames = "tags", allEntries = true)
    @Override
    public TagResponseTo create(TagRequestTo tagTo) {
        return tagMapper.getResponseTo(tagRepository.save(tagMapper.getTag(tagTo)));
    }

    @CacheEvict(cacheNames = "tags", allEntries = true)
    @Override
    public TagResponseTo update(TagRequestTo tagTo) {
        tagRepository
                .findById(tagMapper.getTag(tagTo).getId())
                .orElseThrow(() -> tagNotFoundException(tagMapper.getTag(tagTo).getId()));
        return tagMapper.getResponseTo(tagRepository.save(tagMapper.getTag(tagTo)));
    }

    @Caching(evict = { @CacheEvict(cacheNames = "tags", key = "#id"),
            @CacheEvict(cacheNames = "tags", allEntries = true) })    @Override
    public void removeById(Long id) {
        Tag tag = tagRepository
                .findById(id)
                .orElseThrow(() -> tagNotFoundException(id));
        tagRepository.delete(tag);
    }

    private static ResourceNotFoundException tagNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find tag with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 33);
    }

    private static ResourceStateException tagStateException() {
        return new ResourceStateException("Failed to create/update tag with specified credentials", HttpStatus.CONFLICT.value() * 100 + 34);
    }
}
