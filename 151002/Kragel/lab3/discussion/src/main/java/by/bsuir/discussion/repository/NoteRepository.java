package by.bsuir.discussion.repository;

import by.bsuir.discussion.model.Note;
import by.bsuir.discussion.model.NoteKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends CassandraRepository<Note, NoteKey> {
    @Query(allowFiltering = true)
    Optional<Note> findByKeyId(Long id);
}

