package by.bsuir.dc.features.post;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PostRepository extends CassandraRepository<Post, PostPrimaryKey> {
}