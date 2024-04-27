package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.repository.LabelRepository;
import com.distributed_computing.jpa.bean.Label;
import com.distributed_computing.jpa.exception.NoSuchLabel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    @Autowired
    public LabelService(LabelRepository markerRepository) {
        this.labelRepository = markerRepository;
    }

    public List<Label> getAll(){
        return labelRepository.findAll();
    }

    public Label getById(int id){
        return labelRepository.getReferenceById(id);
    }

    public Label create(Label label){
        labelRepository.save(label);
        return label;
    }

    public Label update(Label label){
        if(!labelRepository.existsById(label.getId())) throw new NoSuchLabel("There is no such marker with this id");
        labelRepository.save(label);
        return label;
    }

    public void delete(int id){
        if(!labelRepository.existsById(id)) throw new NoSuchLabel("There is no such marker with this id");
        labelRepository.deleteById(id);
    }
}