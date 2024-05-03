package com.luschickij.publisher.repository.customjpa;

import com.luschickij.publisher.model.News;
import com.luschickij.publisher.repository.NewsRepository;
import com.luschickij.publisher.repository.jpa.JpaNewsRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomJpaNewsRepository extends CustomJpaRepository<News, Long> implements NewsRepository {


    @Autowired
    public CustomJpaNewsRepository(
            JpaNewsRepository jpaLabelRepository,
            EntityManager entityManager) {
        super(jpaLabelRepository, entityManager, "News");
    }
}
