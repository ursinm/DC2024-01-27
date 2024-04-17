package by.rusakovich.publisher.dao.memory;

import by.rusakovich.publisher.model.entity.impl.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorMemoryRepository extends MemoryEntityRepositoryLongId<Author<Long>> {
}
