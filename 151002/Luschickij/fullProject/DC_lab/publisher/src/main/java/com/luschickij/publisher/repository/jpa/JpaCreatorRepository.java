package com.luschickij.publisher.repository.jpa;

import com.luschickij.publisher.model.Creator;
import com.luschickij.publisher.repository.ICommonRepository;
import com.luschickij.publisher.repository.CreatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCreatorRepository extends JpaRepository<Creator, Long> {
}
