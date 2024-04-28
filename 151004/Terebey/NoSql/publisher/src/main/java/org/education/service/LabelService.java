package org.education.service;

import org.education.bean.Label;
import org.education.exception.NoSuchLabel;
import org.education.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(!labelRepository.existsById(label.getId())) throw new NoSuchLabel("There is no such label with this id");
        labelRepository.save(label);
        return label;
    }

    public void delete(int id){
        if(!labelRepository.existsById(id)) throw new NoSuchLabel("There is no such label with this id");
        labelRepository.deleteById(id);
    }
}