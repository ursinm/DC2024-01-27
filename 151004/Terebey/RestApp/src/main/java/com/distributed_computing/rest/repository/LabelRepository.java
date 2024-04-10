package com.distributed_computing.rest.repository;

import com.distributed_computing.rest.bean.Label;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LabelRepository {

    Map<Integer, Label> labels = new HashMap<>();

    public List<Label> getAll(){
        List<Label> res = new ArrayList<>();

        for(Map.Entry<Integer, Label> entry : labels.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Label> save(Label label){
        Label prevLabel = labels.getOrDefault(label.getId(), null);
        labels.put(label.getId(), label);
        return Optional.ofNullable(prevLabel);
    }


    public Optional<Label> getById(int id){
        return Optional.ofNullable(labels.getOrDefault(id, null));
    }

    public Label delete(int id){
        return labels.remove(id);
    }
}