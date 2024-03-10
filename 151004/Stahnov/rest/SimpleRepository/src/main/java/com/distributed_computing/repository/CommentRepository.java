package com.distributed_computing.repository;

import com.distributed_computing.bean.Comment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CommentRepository {

    Map<Integer, Comment> comments = new HashMap<>();

    public List<Comment> getAll(){
        List<Comment> res = new ArrayList<>();

        for(Map.Entry<Integer, Comment> entry : comments.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Comment> save(Comment comment){
        Comment prevComment = comments.getOrDefault(comment.getId(), null);
        comments.put(comment.getId(), comment);
        return Optional.ofNullable(prevComment);
    }


    public Optional<Comment> getById(int id){
        return Optional.ofNullable(comments.getOrDefault(id, null));
    }

    public Comment delete(int id){
        return comments.remove(id);
    }
}
