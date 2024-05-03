package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.MarkerRepository;
import by.bsuir.publisher.model.entity.Marker;
import by.bsuir.publisher.model.request.MarkerRequestTo;
import by.bsuir.publisher.model.response.MarkerResponseTo;
import by.bsuir.publisher.service.exceptions.ResourceNotFoundException;
import by.bsuir.publisher.service.exceptions.ResourceStateException;
import by.bsuir.publisher.service.mapper.MarkerMapper;
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
@CacheConfig(cacheNames = "markerCache") //использует некоторые общие настройки, связанные с кэшем, на уровне класса
public class MarkerService implements RestService <MarkerRequestTo, MarkerResponseTo> {
    private final MarkerRepository markerRepository;

    private final MarkerMapper markerMapper;
    private final LogWaitSomeTime logWaitSomeTime;

    @Cacheable(cacheNames = "markers") //запускает заполнение кэша
    @Override
    public List<MarkerResponseTo> findAll() {
        logWaitSomeTime.WaitSomeTime(); // лог задержка
        return markerMapper.getListResponseTo(markerRepository.findAll());
    }

    @Cacheable(cacheNames = "marker", key = "#id", unless = "#result == null") //запускает заполнение кэша
    @Override
    public MarkerResponseTo findById(Long id) {
        logWaitSomeTime.WaitSomeTime(); // лог задержка
        return markerMapper.getResponseTo(markerRepository
                .findById(id)
                .orElseThrow(() -> tagNotFoundException(id)));
    }

    @CacheEvict(cacheNames = "markers", allEntries = true) //запускает удаление кэша
    @Override
    public MarkerResponseTo create(MarkerRequestTo tagTo) {
        return markerMapper.getResponseTo(markerRepository.save(markerMapper.getTag(tagTo)));
    }

    @CacheEvict(cacheNames = "markers", allEntries = true) //запускает удаление кэша
    @Override
    public MarkerResponseTo update(MarkerRequestTo tagTo) {
        markerRepository
                .findById(markerMapper.getTag(tagTo).getId())
                .orElseThrow(() -> tagNotFoundException(markerMapper.getTag(tagTo).getId()));
        return markerMapper.getResponseTo(markerRepository.save(markerMapper.getTag(tagTo)));
    }

    //перегруппирует несколько операций кэша, которые будут применены к методу
    @Caching(evict = { @CacheEvict(cacheNames = "marker", key = "#id"),
                       @CacheEvict(cacheNames = "markers", allEntries = true) })
    @Override
    public void removeById(Long id) {
        Marker marker = markerRepository
                .findById(id)
                .orElseThrow(() -> tagNotFoundException(id));
        markerRepository.delete(marker);
    }

    private static ResourceNotFoundException tagNotFoundException(Long id) {
        return new ResourceNotFoundException("Failed to find tag with id = " + id, HttpStatus.NOT_FOUND.value() * 100 + 33);
    }

    private static ResourceStateException tagStateException() {
        return new ResourceStateException("Failed to create/update tag with specified credentials", HttpStatus.CONFLICT.value() * 100 + 34);
    }
}
