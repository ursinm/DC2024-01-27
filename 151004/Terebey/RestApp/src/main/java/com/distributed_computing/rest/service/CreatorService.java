package com.distributed_computing.rest.service;

import com.distributed_computing.rest.repository.CreatorRepository;
import com.distributed_computing.rest.bean.Creator;
import com.distributed_computing.rest.bean.dto.CreatorResponseTo;
import com.distributed_computing.rest.bean.Issue;
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
    private final IssueService issueService ;
    private final ModelMapper modelMapper;

    @Autowired
    public CreatorService(CreatorRepository creatorRepository, IssueService issueService, ModelMapper modelMapper) {
        this.creatorRepository = creatorRepository;
        this.issueService = issueService;
        this.modelMapper = modelMapper;
    }

    public Optional<Creator> getCreatorByIssueId(int id){
        Optional<Issue> issueOptional = issueService.getById(id);
        if(issueOptional.isPresent()){
            return creatorRepository.getById(issueOptional.get().getCreatorId());
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