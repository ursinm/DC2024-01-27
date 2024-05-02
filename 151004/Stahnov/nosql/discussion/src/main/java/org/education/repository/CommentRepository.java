package org.education.repository;

import org.education.bean.Comment;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    @AllowFiltering
    Optional<Comment> findCommentByKey(int key);
    @AllowFiltering
    boolean existsByKey(int key);
}
