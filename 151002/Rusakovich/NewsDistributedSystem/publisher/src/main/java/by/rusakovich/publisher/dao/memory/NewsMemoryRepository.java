package by.rusakovich.publisher.dao.memory;

import by.rusakovich.publisher.model.entity.impl.News;
import org.springframework.stereotype.Repository;

@Repository
public class NewsMemoryRepository extends MemoryEntityRepositoryLongId<News<Long>> {
}
