package by.bsuir.dc.publisher.dal;

import by.bsuir.dc.publisher.entities.dtos.response.LabelResponseTo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisLabelDao extends CrudRepository<LabelResponseTo, Long> {
}
