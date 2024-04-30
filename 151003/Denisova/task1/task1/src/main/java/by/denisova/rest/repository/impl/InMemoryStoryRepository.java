package by.denisova.rest.repository.impl;

import by.denisova.rest.model.Story;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStoryRepository extends AbstractInMemoryRepository<Story> {
}
