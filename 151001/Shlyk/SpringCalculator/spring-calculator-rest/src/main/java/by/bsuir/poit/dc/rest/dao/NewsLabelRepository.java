package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.NewsLabel;
import by.bsuir.poit.dc.rest.model.NewsLabelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsLabelRepository extends JpaRepository<NewsLabel, NewsLabelId> {
    boolean existsByNewsIdAndLabelId(long newsId, long labelId);

    void deleteByNewsIdAndLabelId(long newsId, long labelId);
}