package com.example.storyteller.repository;

import com.example.storyteller.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
