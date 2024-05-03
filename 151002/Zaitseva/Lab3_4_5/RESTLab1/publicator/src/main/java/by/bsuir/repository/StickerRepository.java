package by.bsuir.repository;

import by.bsuir.entities.Sticker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long>, JpaSpecificationExecutor<Sticker> {
    @Query("SELECT i.stickers FROM Issue i WHERE i.id = :issueId")
    List<Sticker> findStickersByIssueId(@Param("issueId") Long issueId);

    Page<Sticker> findAll(Pageable pageable);
}
