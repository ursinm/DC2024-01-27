package com.luschickij.publisher.repository.jpa;

import com.luschickij.publisher.model.Label;
import com.luschickij.publisher.repository.LabelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface JpaLabelRepository extends JpaRepository<Label, Long> {

    @Query("SELECT m FROM Label m WHERE m.id IN (:ids)")
    List<Label> findByIdIn(List<Long> ids);
}
