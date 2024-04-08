package by.bsuir.poit.dc.cassandra.dao;

import by.bsuir.poit.dc.cassandra.model.NoteByNews;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 08/03/2024
 */
public interface NoteByNewsRepository extends MapIdCassandraRepository<NoteByNews> {
    List<NoteByNews> findByNewsId(long newsId);

    Optional<NoteByNews> findByIdAndNewsId(long id, long newsId);

    void deleteByIdAndNewsId(long id, long newsId);
}
