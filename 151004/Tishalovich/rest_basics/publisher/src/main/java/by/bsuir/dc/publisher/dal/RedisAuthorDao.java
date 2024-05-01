package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.dtos.response.AuthorResponseTo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisAuthorDao extends CrudRepository<AuthorResponseTo, Long> {
}
