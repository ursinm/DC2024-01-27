package org.education.service;

import org.education.bean.Creator;
import org.education.bean.DTO.CreatorResponseTo;
import org.education.bean.DTO.MarkerResponseTo;
import org.education.bean.Marker;
import org.education.exception.NoSuchMarker;
import org.education.repository.MarkerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "markerCache")
public class MarkerService {

    private final MarkerRepository markerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MarkerService(MarkerRepository markerRepository, ModelMapper modelMapper) {
        this.markerRepository = markerRepository;
        this.modelMapper = modelMapper;
    }

    @Cacheable(cacheNames = "markers")
    public List<MarkerResponseTo> getAll(){
        List<MarkerResponseTo> res = new ArrayList<>();
        for(Marker marker : markerRepository.findAll()){
            res.add(modelMapper.map(marker, MarkerResponseTo.class));
        }
        return res;
    }
    @Cacheable(cacheNames = "markers", key = "#id", unless = "#result == null")
    public MarkerResponseTo getById(int id){
        return modelMapper.map(markerRepository.getReferenceById(id), MarkerResponseTo.class);
    }

    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo create(Marker marker){
        markerRepository.save(marker);
        return modelMapper.map(marker, MarkerResponseTo.class);
    }

    @CacheEvict(cacheNames = "markers", allEntries = true)
    public MarkerResponseTo update(Marker marker){
        if(!markerRepository.existsById(marker.getId())) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.save(marker);
        return modelMapper.map(marker, MarkerResponseTo.class);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "markers", key = "#id"),
            @CacheEvict(cacheNames = "markers", allEntries = true) })
    public void delete(int id){
        if(!markerRepository.existsById(id)) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.deleteById(id);
    }
}
