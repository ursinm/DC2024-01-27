package by.bsuir.dc.repository;

import by.bsuir.dc.entity.Creator;
import by.bsuir.dc.repository.common.InMemoryCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CreatorRepository extends InMemoryCrudRepository<Creator> {
}
