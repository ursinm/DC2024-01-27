package application.services.impl;

import application.dto.MarkerRequestTo;
import application.dto.MarkerResponseTo;
import application.entites.Marker;
import application.exceptions.DeleteException;
import application.exceptions.NotFoundException;
import application.exceptions.UpdateException;
import application.mappers.MarkerListMapper;
import application.mappers.MarkerMapper;
import application.repository.MarkerRepository;
import application.services.MarkerService;
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
public class ImplMarkerService implements MarkerService {

    @Autowired
    MarkerMapper markerMapper;

    @Autowired
    MarkerRepository markerRepository;

    @Autowired
    MarkerListMapper markerListMapper;

    @Override
    @Cacheable(cacheNames = "markers", key = "#id", unless = "#result == null")
    public MarkerResponseTo getById(Long id) throws NotFoundException {
        Optional<Marker> marker = markerRepository.findById(id);
        return marker.map(value -> markerMapper.toMarkerResponse(value)).orElseThrow(() -> new NotFoundException("Marker not found", 40004L));
    }

    @Override
    @Cacheable(cacheNames = "markers")
    public List<MarkerResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable;
        if (sortOrder != null && sortOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Marker> markers = markerRepository.findAll(pageable);
        return markerListMapper.toMarkerResponseList(markers.toList());
    }

    @Override
    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo save(@Valid MarkerRequestTo requestTo) {
        Marker markerToSave = markerMapper.toMarker(requestTo);
        return markerMapper.toMarkerResponse(markerRepository.save(markerToSave));
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = "markers", key = "#id"),
            @CacheEvict(cacheNames = "markers", allEntries = true) })
    public void delete(Long id) throws DeleteException {
        if (!markerRepository.existsById(id)) {
            throw new DeleteException("Marker not found!", 40004L);
        } else {
            markerRepository.deleteById(id);
        }
    }

    @Override
    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo update(@Valid MarkerRequestTo requestTo) throws UpdateException {
        Marker markerToUpdate = markerMapper.toMarker(requestTo);
        if(!markerRepository.existsById(markerToUpdate.getId())) {
            throw new UpdateException("Marker not found!", 40004L);
        } else {
            return markerMapper.toMarkerResponse(markerRepository.save(markerToUpdate));
        }
    }

    @Override
    public List<MarkerResponseTo> getByStoryId(Long storyId) throws NotFoundException {
        List<Marker> markers = markerRepository.findMarkersByStoryId(storyId);
        return markerListMapper.toMarkerResponseList(markers);
    }
}
