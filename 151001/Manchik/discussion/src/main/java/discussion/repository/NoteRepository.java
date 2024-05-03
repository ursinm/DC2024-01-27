package discussion.repository;

import discussion.entities.Note;
import discussion.entities.NoteKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface NoteRepository extends CassandraRepository<Note, NoteKey> {
    @Query("select * from tbl_note where story_id = ?0 allow filtering;")
    List<Note> findByStoryId(Long story_id);
    @Query("select * from tbl_note where id = ?0 allow filtering;")
    List<Note> findById(Long id);
    void deleteByCountryAndStoryIdAndId (String country, Long storyId, Long id);
    int countByCountry(String country);
}