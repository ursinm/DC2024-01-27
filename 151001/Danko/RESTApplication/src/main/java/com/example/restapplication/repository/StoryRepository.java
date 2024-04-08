package com.example.restapplication.repository;

import com.example.restapplication.entites.Story;
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
            "LEFT JOIN story.tags tags " +
            "WHERE (:tagNames IS NULL OR tags.name IN :tagNames) " +
            "AND (:tagIds IS NULL OR tags.id IN :tagIds) " +
            "AND (:userLogin IS NULL OR story.user.login = :userLogin) " +
            "AND (:title IS NULL OR story.title LIKE %:title%) " +
            "AND (:content IS NULL OR story.content LIKE %:content%)")
    List<Story> findStoriesByParams(@Param("tagNames") List<String> tagNames,
                                   @Param("tagIds") List<Long> tagIds,
                                   @Param("userLogin") String userLogin,
                                   @Param("title") String title,
                                   @Param("content") String content);
    Page<Story> findAll(Pageable pageable);
}
