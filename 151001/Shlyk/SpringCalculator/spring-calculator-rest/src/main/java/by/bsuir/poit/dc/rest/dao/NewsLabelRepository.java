package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.NewsLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsLabelRepository extends JpaRepository<NewsLabel, Long> {
}