package com.distributed_computing.jpa.service;


import com.distributed_computing.jpa.bean.Creator;
import com.distributed_computing.jpa.bean.Tweet;
import com.distributed_computing.jpa.exception.AlreadyExists;
import com.distributed_computing.jpa.exception.NoSuchCreator;
import com.distributed_computing.jpa.repository.CreatorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreatorService {

    private static int ind = 0;

    private final CreatorRepository creatorRepository;
    private final TweetService tweetService ;
    private final ModelMapper modelMapper;

    @Autowired
    public CreatorService(CreatorRepository creatorRepository, TweetService tweetService, ModelMapper modelMapper) {
        this.creatorRepository = creatorRepository;
        this.tweetService = tweetService;
        this.modelMapper = modelMapper;
    }

    /*
    public Optional<Creator> getCreatorByTweetId(int id){
        Optional<Tweet> tweetOptional = tweetService.getById(id);
        if(tweetOptional.isPresent()){
            return creatorRepository.getById(tweetOptional.get().getCreatorId());
        }
        else{
            return Optional.empty();
        }
    }
     */

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
