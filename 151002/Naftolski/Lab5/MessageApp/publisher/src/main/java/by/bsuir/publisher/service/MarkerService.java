package by.bsuir.publisher.service;

import by.bsuir.publisher.dao.repository.MarkerRepository;
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
@CacheConfig(cacheNames = "markerCache")
public class MarkerService implements IService<MarkerRequestTo, MarkerResponseTo> {
    private final MarkerRepository markerRepository;
    private final MarkerMapper markerMapper;

    @Cacheable(cacheNames = "markers", key = "#id", unless = "#result == null")
    @Override
    public MarkerResponseTo findById(Long id) {
        return markerRepository.findById(id).map(markerMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Cacheable(cacheNames = "markers")
    @Override
    public List<MarkerResponseTo> findAll() {
        return markerMapper.getListResponse(markerRepository.findAll());
    }

    @CacheEvict(cacheNames = "markers", allEntries = true)
    @Override
    public MarkerResponseTo create(MarkerRequestTo request) {
        MarkerResponseTo response = markerMapper.getResponse(markerRepository.save(markerMapper.getMarker(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @CacheEvict(cacheNames = "markers", allEntries = true)
    @Override
    public MarkerResponseTo update(MarkerRequestTo request) {
        MarkerResponseTo response = markerMapper.getResponse(markerRepository.save(markerMapper.getMarker(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @CacheEvict(cacheNames = "markers", key = "#id")
    @Override
    public void removeById(Long id) {
        Marker marker = markerRepository.findById(id).orElseThrow(MarkerService::removeException);

        markerRepository.delete(marker);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 21, "Can't find marker by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 22, "Can't create marker");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 23, "Can't update marker");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 24, "Can't remove marker");
    }
}
