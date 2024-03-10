package com.distributed_computing.rest.service;

import com.distributed_computing.repository.CreatorRepository;
import com.distributed_computing.bean.Creator;
import com.distributed_computing.bean.DTO.CreatorResponseTo;
import com.distributed_computing.bean.Tweet;
import com.distributed_computing.rest.exception.NoSuchCreator;
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

    public Optional<Creator> getCreatorByTweetId(int id){
        Optional<Tweet> tweetOptional = tweetService.getById(id);
        if(tweetOptional.isPresent()){
            return creatorRepository.getById(tweetOptional.get().getCreatorId());
        }
        else{
            return Optional.empty();
        }
    }

    public List<CreatorResponseTo> getAll(){
        List<CreatorResponseTo> response = new ArrayList<>();
        for(Creator creator : creatorRepository.getAll()){
            response.add(modelMapper.map(creator, CreatorResponseTo.class));
        }
        return response;
    }

    public Optional<Creator> getById(int id){
        return creatorRepository.getById(id);
    }

    public Creator create(Creator creator){
        creator.setId(ind++);
        creatorRepository.save(creator);
        return creator;
    }

    public Creator update(Creator creator){
        if(creatorRepository.getById(creator.getId()).isEmpty()) throw new NoSuchCreator("There is no such creator with this id");
        creatorRepository.save(creator);
        return creator;
    }

    public void delete(int id){
        if(creatorRepository.delete(id) == null) throw new NoSuchCreator("There is no such creator with this id");
    }
}
