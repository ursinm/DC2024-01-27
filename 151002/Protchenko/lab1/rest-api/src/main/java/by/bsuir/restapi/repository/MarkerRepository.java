package by.bsuir.restapi.repository;

import by.bsuir.restapi.model.entity.Marker;
import by.bsuir.restapi.repository.base.InMemoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarkerRepository extends InMemoryRepository<Marker> {
}
