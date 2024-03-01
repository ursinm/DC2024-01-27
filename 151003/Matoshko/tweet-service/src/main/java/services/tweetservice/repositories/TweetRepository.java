package services.tweetservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import services.tweetservice.domain.entity.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
