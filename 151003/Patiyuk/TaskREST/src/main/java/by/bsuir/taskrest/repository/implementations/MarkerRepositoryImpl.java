package by.bsuir.taskrest.repository.implementations;

import by.bsuir.taskrest.entity.Marker;
import by.bsuir.taskrest.repository.MarkerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarkerRepositoryImpl extends InMemoryRepository<Marker> implements MarkerRepository {
}
