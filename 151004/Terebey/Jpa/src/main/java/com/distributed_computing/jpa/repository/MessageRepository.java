package com.distributed_computing.jpa.repository;

import com.distributed_computing.jpa.bean.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}