package application.repository;

import application.entites.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MarkerRepository extends JpaRepository<Marker, Long>, JpaSpecificationExecutor<Marker> {
    @Query("SELECT story.markers FROM Story story WHERE story.id = :storyId")
    List<Marker> findMarkersByStoryId(@Param("storyId") Long storyId);
    Page<Marker> findAll(Pageable pageable);
}
