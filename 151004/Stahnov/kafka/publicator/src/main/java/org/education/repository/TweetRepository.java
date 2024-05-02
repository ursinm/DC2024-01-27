package org.education.repository;

import org.education.bean.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    boolean existsTweetByTitle(String title);
}
