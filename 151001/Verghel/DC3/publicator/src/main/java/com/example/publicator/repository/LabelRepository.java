package com.example.publicator.repository;

import com.example.publicator.model.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabelRepository extends JpaRepository<Label,Integer> {
    Page<Label> findAll(Pageable pageable);
}
