package com.distributed_computing.rest.service;

import com.distributed_computing.repository.MarkerRepository;
import com.distributed_computing.bean.Marker;
import com.distributed_computing.rest.exception.NoSuchMarker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkerService {
    private static int ind = 0;

    private final MarkerRepository markerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MarkerService(MarkerRepository markerRepository, ModelMapper modelMapper) {
        this.markerRepository = markerRepository;
        this.modelMapper = modelMapper;
    }

    public List<Marker> getAll(){
        return markerRepository.getAll();
    }

    public Optional<Marker> getById(int id){
        return markerRepository.getById(id);
    }

    public Marker create(Marker marker){
        marker.setId(ind++);
        markerRepository.save(marker);
        return marker;
    }

    public Marker update(Marker marker){
        if(markerRepository.getById(marker.getId()).isEmpty()) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.save(marker);
        return marker;
    }

    public void delete(int id){
        if(markerRepository.delete(id) == null) throw new NoSuchMarker("There is no such marker with this id");
    }
}
