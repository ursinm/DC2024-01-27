package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}