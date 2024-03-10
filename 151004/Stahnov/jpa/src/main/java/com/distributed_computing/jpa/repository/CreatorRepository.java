package com.distributed_computing.jpa.repository;

import com.distributed_computing.jpa.bean.Creator;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Integer> {

    boolean existsCreatorByLogin(String login);
}
