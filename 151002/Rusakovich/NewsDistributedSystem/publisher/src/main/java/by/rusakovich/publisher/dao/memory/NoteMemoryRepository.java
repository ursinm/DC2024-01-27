package by.rusakovich.publisher.dao.memory;

import by.rusakovich.publisher.model.entity.impl.Note;
import org.springframework.stereotype.Repository;

@Repository
public class NoteMemoryRepository extends MemoryEntityRepositoryLongId<Note<Long>>{
}
