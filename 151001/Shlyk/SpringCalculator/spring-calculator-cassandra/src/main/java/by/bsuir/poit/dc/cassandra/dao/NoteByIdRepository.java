package by.bsuir.poit.dc.cassandra.dao;

import by.bsuir.poit.dc.cassandra.model.NoteById;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import java.util.Optional;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
public interface NoteByIdRepository extends MapIdCassandraRepository<NoteById> {
    default Optional<NoteById> findById(long id) {
	return findById(BasicMapId.id("id", id));
    }

    default void deleteById(long id) {
	deleteById(BasicMapId.id("id", id));
    }
}
