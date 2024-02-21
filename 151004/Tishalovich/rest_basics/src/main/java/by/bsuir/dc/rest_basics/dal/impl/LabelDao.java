package by.bsuir.dc.rest_basics.dal.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Label;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class LabelDao extends MemoryRepository<Label> {

    @Override
    public Optional<Label> update(Label label) {
        Long id = label.getId();
        Label memoryLabel = map.get(id);

        if (label.getName() != null) {
            memoryLabel.setName(label.getName());
        }

        return Optional.of(memoryLabel);
    }

}
