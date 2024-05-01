package by.bsuir.news.repository;

import by.bsuir.news.entity.NewsMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMarkerRepository extends JpaRepository<NewsMarker, Long> {
}
