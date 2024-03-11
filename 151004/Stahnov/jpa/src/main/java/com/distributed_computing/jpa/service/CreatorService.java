package com.distributed_computing.jpa.service;

import com.distributed_computing.jpa.bean.Creator;
import com.distributed_computing.jpa.exception.AlreadyExists;
import com.distributed_computing.jpa.exception.NoSuchCreator;
import com.distributed_computing.jpa.repository.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;

    @Autowired
    public CreatorService(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    public List<Creator> getAll(){
        return creatorRepository.findAll();
    }

    public Creator getById(int id){
        return creatorRepository.getReferenceById(id);
    }

    public Creator create(Creator creator){
        if(creatorRepository.existsCreatorByLogin(creator.getLogin())) throw new AlreadyExists("Creator with this login already exists");
        creatorRepository.save(creator);
        return creator;
    }

    public Creator update(Creator creator){
        if(!creatorRepository.existsById(creator.getId())) throw new NoSuchCreator("There is no such creator with this id");
        creatorRepository.save(creator);
        return creator;
    }

    public void delete(int id){
        if(!creatorRepository.existsById(id)) throw new NoSuchCreator("There is no such creator with this id");
        creatorRepository.deleteById(id);
    }
}
