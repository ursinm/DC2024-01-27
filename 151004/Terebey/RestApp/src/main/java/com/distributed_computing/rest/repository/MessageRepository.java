package com.distributed_computing.rest.repository;

import com.distributed_computing.rest.bean.Message;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MessageRepository {

    Map<Integer, Message> messages = new HashMap<>();

    public List<Message> getAll(){
        List<Message> res = new ArrayList<>();

        for(Map.Entry<Integer, Message> entry : messages.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Message> save(Message message){
        Message prevMessage = messages.getOrDefault(message.getId(), null);
        messages.put(message.getId(), message);
        return Optional.ofNullable(prevMessage);
    }


    public Optional<Message> getById(int id){
        return Optional.ofNullable(messages.getOrDefault(id, null));
    }

    public Message delete(int id){
        return messages.remove(id);
    }
}