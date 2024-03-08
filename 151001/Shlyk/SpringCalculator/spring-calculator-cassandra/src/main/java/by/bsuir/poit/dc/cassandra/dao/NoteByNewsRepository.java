package by.bsuir.poit.dc.cassandra.dao;

import by.bsuir.poit.dc.cassandra.model.NoteByNews;
import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * @author Paval Shlyk
 * @since 08/03/2024
 */
public interface NoteByNewsRepository extends CassandraRepository<Long, NoteByNews> {
}
