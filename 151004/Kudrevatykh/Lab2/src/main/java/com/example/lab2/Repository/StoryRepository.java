package com.example.lab2.Repository;

import com.example.lab2.Model.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoryRepository extends JpaRepository<Story,Integer> {

    Page<Story> findAll (Pageable pageable);
    boolean existsByTitle(String title);

}


