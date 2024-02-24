package com.distributed_computing.rest.repository;

import com.distributed_computing.rest.bean.Creator;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CreatorRepository {

    Map<Integer, Creator> creators = new HashMap<>();

    public List<Creator> getAll(){
        List<Creator> res = new ArrayList<>();

        for(Map.Entry<Integer, Creator> entry : creators.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Creator> save(Creator creator){
        Creator prevCreator = creators.getOrDefault(creator.getId(), null);
        creators.put(creator.getId(), creator);
        return Optional.ofNullable(prevCreator);
    }


    public Optional<Creator> getById(int id){
        return Optional.ofNullable(creators.getOrDefault(id, null));
    }

    public Creator delete(int id){
        return creators.remove(id);
    }
}
