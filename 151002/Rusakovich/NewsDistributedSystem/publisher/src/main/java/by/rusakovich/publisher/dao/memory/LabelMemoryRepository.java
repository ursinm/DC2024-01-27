package by.rusakovich.publisher.dao.memory;

import by.rusakovich.publisher.model.entity.impl.Label;
import org.springframework.stereotype.Repository;

@Repository
public class LabelMemoryRepository extends MemoryEntityRepositoryLongId<Label<Long>> {
}
