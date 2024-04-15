package by.bsuir.messageapp.dao.repository;

import by.bsuir.messageapp.model.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

}
