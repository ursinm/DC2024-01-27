package com.example.lab2.repository;

import com.example.lab2.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag,Integer> {
    Page<Tag> findAll(Pageable pageable);
}
