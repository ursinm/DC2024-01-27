package com.distributed_computing.rest.service;

import com.distributed_computing.rest.repository.LabelRepository;
import com.distributed_computing.rest.bean.Label;
import com.distributed_computing.rest.exception.NoSuchLabel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {
    private static int ind = 0;

    private final LabelRepository labelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LabelService(LabelRepository labelRepository, ModelMapper modelMapper) {
        this.labelRepository = labelRepository;
        this.modelMapper = modelMapper;
    }

    public List<Label> getAll(){
        return labelRepository.getAll();
    }

    public Optional<Label> getById(int id){
        return labelRepository.getById(id);
    }

    public Label create(Label label){
        label.setId(ind++);
        labelRepository.save(label);
        return label;
    }

    public Label update(Label label){
        if(labelRepository.getById(label.getId()).isEmpty()) throw new NoSuchLabel("There is no such label with this id");
        labelRepository.save(label);
        return label;
    }

    public void delete(int id){
        if(labelRepository.delete(id) == null) throw new NoSuchLabel("There is no such label with this id");
    }
}