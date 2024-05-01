package by.bsuir.restapi.repository;

import by.bsuir.restapi.model.entity.Post;
import by.bsuir.restapi.repository.base.InMemoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository extends InMemoryRepository<Post> {
}
