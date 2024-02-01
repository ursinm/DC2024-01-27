package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select user from User user left join " +
	       "News news on news.author.id = user.id " +
	       "where news.id = :news_id")
    Optional<User> findByNewsId(
	@Param("news_id") long newsId);
}