package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.bean.Marker;
import com.distributed_computing.jpa.exception.NoSuchMarker;
import com.distributed_computing.jpa.repository.MarkerRepository;
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
        return markerRepository.findAll();
    }

    public Marker getById(int id){
        return markerRepository.getReferenceById(id);
    }

    public Marker create(Marker marker){
        markerRepository.save(marker);
        return marker;
    }

    public Marker update(Marker marker){
        if(!markerRepository.existsById(marker.getId())) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.save(marker);
        return marker;
    }

    public void delete(int id){
        if(!markerRepository.existsById(id)) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.deleteById(id);
    }
}
