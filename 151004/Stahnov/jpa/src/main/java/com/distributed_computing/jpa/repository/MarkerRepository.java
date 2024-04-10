package com.distributed_computing.jpa.repository;

import com.distributed_computing.jpa.bean.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Integer> {
}
