package by.bsuir.taskrest.repository.implementations;

import by.bsuir.taskrest.entity.Creator;
import by.bsuir.taskrest.repository.CreatorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CreatorRepositoryImpl extends InMemoryRepository<Creator> implements CreatorRepository {
}
