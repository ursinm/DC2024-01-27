package com.distributed_computing.jpa.repository;

import com.distributed_computing.jpa.bean.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
}