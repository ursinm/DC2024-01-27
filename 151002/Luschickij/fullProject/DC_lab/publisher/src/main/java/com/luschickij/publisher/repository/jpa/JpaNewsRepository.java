package com.luschickij.publisher.repository.jpa;

import com.luschickij.publisher.model.News;
import com.luschickij.publisher.repository.NewsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface JpaNewsRepository extends JpaRepository<News, Long> {
}
