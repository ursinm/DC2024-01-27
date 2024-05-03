package org.example.dc.model;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface NoteRepository extends CassandraRepository<Note, Integer> {
}