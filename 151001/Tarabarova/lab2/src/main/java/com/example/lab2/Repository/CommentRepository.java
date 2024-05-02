package com.example.lab2.Repository;

import com.example.lab2.Model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findAll(Pageable pageable);
}
