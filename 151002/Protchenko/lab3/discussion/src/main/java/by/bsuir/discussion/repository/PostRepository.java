package by.bsuir.discussion.repository;

import by.bsuir.discussion.model.entity.Post;
import by.bsuir.discussion.model.entity.PostKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CassandraRepository<Post, PostKey> {

    @Query(allowFiltering = true)
    Optional<Post> findByKeyId(Long id);
}
