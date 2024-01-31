package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}