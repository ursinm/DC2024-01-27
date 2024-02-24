package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.News;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByUserId(long id);

    @EntityGraph(attributePaths = {"notes"})
    Optional<News> findWithNotesById(long id);

    @EntityGraph(attributePaths = {"labels"})
    Optional<News> findWithLabelsById(long id);
}