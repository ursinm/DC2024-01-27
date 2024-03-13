package com.distributed_computing.jpa.repository;

import com.distributed_computing.jpa.bean.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    boolean existsTweetByTitle(String title);
}
