package com.example.rw.repository.implementations.jpa;

import com.example.rw.model.entity.implementations.News;
import com.example.rw.repository.interfaces.NewsRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface CustomNewsRepository extends JpaRepository<News,Long> , NewsRepository {
}
