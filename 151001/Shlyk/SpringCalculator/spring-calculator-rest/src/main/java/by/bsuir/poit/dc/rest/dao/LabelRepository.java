package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findByName(String name);

    @Query("select label from Label label left join " +
	       "NewsLabel newsLabel on newsLabel.label.id = label.id " +
	       "where newsLabel.news.id = :news_id")
    List<Label> findByNewsId(@Param("news_id") long newsId);
}