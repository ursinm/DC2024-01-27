package by.rusakovich.newsdistributedsystem.dao.memory;

import by.rusakovich.newsdistributedsystem.model.entity.impl.Note;
import org.springframework.stereotype.Repository;

@Repository
public class NoteMemoryRepository extends MemoryEntityRepositoryLongId<Note<Long>>{
}
