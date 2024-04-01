package by.bsuir.dc.rest_basics.dal;

import by.bsuir.dc.rest_basics.entities.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelDao extends CrudRepository<Label, Long> {
}
