package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.StoryRepository;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@CacheConfig(cacheNames = "storyCache") //использует некоторые общие настройки, связанные с кэшем, на уровне класса
public class StoryService implements RestService<StoryRequestTo, StoryResponseTo> {
    private final StoryRepository storyRepository;

    private final StoryMapper storyMapper;
    private final LogWaitSomeTime logWaitSomeTime;

    @Cacheable(cacheNames = "storys") //запускает заполнение кэша
    @Override
    public List<StoryResponseTo> findAll() {
        logWaitSomeTime.WaitSomeTime(); // лог задержка
        return storyMapper.getListResponseTo(storyRepository.findAll());
    }

    @Cacheable(cacheNames = "story", key = "#id", unless = "#result == null") //запускает заполнение кэша
    @Override
    public StoryResponseTo findById(Long id) {
        logWaitSomeTime.WaitSomeTime(); // лог задержка
        return storyMapper.getResponseTo(storyRepository
                .findById(id)
                .orElseThrow(() -> newsNotFoundException(id)));
    }

    @CacheEvict(cacheNames = "storys", allEntries = true) //запускает удаление кэша
    @Override
    public StoryResponseTo create(StoryRequestTo newsTo) {
        Story story = storyMapper.getNews(newsTo);
        story.setCreated(LocalDateTime.now());
        story.setModified(story.getCreated());
        return storyMapper.getResponseTo(storyRepository.save(story));
    }

    @CacheEvict(cacheNames = "storys", allEntries = true) //запускает удаление кэша
    @Override
    public StoryResponseTo update(StoryRequestTo newsTo) {
        storyRepository
                .findById(storyMapper.getNews(newsTo).getId())
                .orElseThrow(() -> newsNotFoundException(storyMapper.getNews(newsTo).getId()));
        return storyMapper.getResponseTo(storyRepository.save(storyMapper.getNews(newsTo)));
    }

    //перегруппирует несколько операций кэша, которые будут применены к методу
    @Caching(evict = { @CacheEvict(cacheNames = "story", key = "#id"),
                       @CacheEvict(cacheNames = "storys", allEntries = true) })
    @Override
    public void removeById(Long id) {
        Story story = storyRepository
                .findById(id)
                .orElseThrow(() -> newsNotFoundException(id));
        storyRepository.delete(story);
    }

    private static ResourceNotFoundException newsNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find news with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 53);
    }

    private static ResourceStateException newsStateException() {
        return new ResourceStateException("Failed to create/update news with specified credentials", HttpStatus.CONFLICT.value() * 100 + 54);
    }
}
