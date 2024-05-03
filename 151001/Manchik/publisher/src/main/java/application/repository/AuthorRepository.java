package application.repository;

import application.entites.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    boolean existsByLogin (String login);
    @Query("SELECT story.author FROM Story story WHERE story.id = :storyId")
    Optional<Author> findAuthorByStory(@Param("storyId") Long storyId);
    Page<Author> findAll(Pageable pageable);
}
