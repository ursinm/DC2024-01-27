package com.luschickij.publisher.repository.customjpa;

import com.luschickij.publisher.model.Creator;
import com.luschickij.publisher.repository.CreatorRepository;
import com.luschickij.publisher.repository.jpa.JpaCreatorRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomJpaCreatorRepository extends CustomJpaRepository<Creator, Long> implements CreatorRepository {

    @Autowired
    public CustomJpaCreatorRepository(JpaCreatorRepository jpaLabelRepository,
                                   EntityManager entityManager) {
        super(jpaLabelRepository, entityManager, "Creator");
    }
}
