package services.tweetservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import services.tweetservice.domain.entity.Creator;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {
    Optional<Creator> findCreatorById(Long id);
}
