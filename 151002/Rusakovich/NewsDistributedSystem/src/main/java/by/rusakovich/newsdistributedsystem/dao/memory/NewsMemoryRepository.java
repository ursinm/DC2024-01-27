package by.rusakovich.newsdistributedsystem.dao.memory;

import by.rusakovich.newsdistributedsystem.model.entity.impl.News;
import org.springframework.stereotype.Repository;

@Repository
public class NewsMemoryRepository extends MemoryEntityRepositoryLongId<News> {
}
