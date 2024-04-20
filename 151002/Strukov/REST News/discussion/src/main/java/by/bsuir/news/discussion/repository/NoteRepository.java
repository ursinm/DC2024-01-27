package by.bsuir.news.discussion.repository;

import by.bsuir.news.discussion.entity.Note;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface NoteRepository extends CassandraRepository<Note, Long> {
}
