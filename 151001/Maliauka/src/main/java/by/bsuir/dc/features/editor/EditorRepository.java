package by.bsuir.dc.features.editor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditorRepository extends JpaRepository<Editor, Long> {

    boolean existsByLogin(String login);

    Optional<Editor> findByNewsId(long newsId);
}