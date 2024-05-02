package com.luschickij.publisher.repository.customjpa;

import com.luschickij.publisher.model.Label;
import com.luschickij.publisher.repository.LabelRepository;
import com.luschickij.publisher.repository.exception.EntityNotFoundException;
import com.luschickij.publisher.repository.jpa.JpaLabelRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomJpaLabelRepository extends CustomJpaRepository<Label, Long> implements LabelRepository {


    @Autowired
    public CustomJpaLabelRepository(JpaLabelRepository jpaLabelRepository,
                                     EntityManager entityManager) {
        super(jpaLabelRepository, entityManager, "Label");
    }

    public List<Label> findByIdIn(List<Long> ids) {
        return ((JpaLabelRepository) jpaLabelRepository).findByIdIn(ids);
    }
}
