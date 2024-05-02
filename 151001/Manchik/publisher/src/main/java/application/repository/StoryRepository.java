package application.repository;

import application.entites.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StoryRepository extends JpaRepository<Story, Long>, JpaSpecificationExecutor<Story> {
    boolean existsByTitle(String title);
    @Query("SELECT DISTINCT story FROM Story story " +
            "LEFT JOIN story.markers markers " +
            "WHERE (:markerNames IS NULL OR markers.name IN :markerNames) " +
            "AND (:markerIds IS NULL OR markers.id IN :markerIds) " +
            "AND (:authorLogin IS NULL OR story.author.login = :authorLogin) " +
            "AND (:title IS NULL OR story.title LIKE %:title%) " +
            "AND (:content IS NULL OR story.content LIKE %:content%)")
    List<Story> findStoriesByParams(@Param("markerNames") List<String> markerNames,
                                   @Param("markerIds") List<Long> markerIds,
                                   @Param("authorLogin") String authorLogin,
                                   @Param("title") String title,
                                   @Param("content") String content);
    Page<Story> findAll(Pageable pageable);
}
