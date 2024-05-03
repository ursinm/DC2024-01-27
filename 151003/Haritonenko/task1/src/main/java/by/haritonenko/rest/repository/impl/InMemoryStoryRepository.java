package by.haritonenko.rest.repository.impl;

import by.haritonenko.rest.model.Story;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStoryRepository extends AbstractInMemoryRepository<Story> {
}
