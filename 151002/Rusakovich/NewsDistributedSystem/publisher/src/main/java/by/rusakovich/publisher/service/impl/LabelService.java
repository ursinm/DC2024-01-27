package by.rusakovich.publisher.service.impl;

import by.rusakovich.publisher.dao.IEntityRepository;
import by.rusakovich.publisher.model.dto.label.LabelRequestTO;
import by.rusakovich.publisher.model.dto.label.LabelResponseTO;
import by.rusakovich.publisher.model.dto.mapper.EntityMapper;
import by.rusakovich.publisher.model.entity.impl.jpa.JpaLabel;
import by.rusakovich.publisher.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class LabelService extends EntityService<Long, LabelRequestTO, LabelResponseTO, JpaLabel> {
    public LabelService(EntityMapper<Long, JpaLabel, LabelRequestTO, LabelResponseTO> mapper, IEntityRepository<Long, JpaLabel> rep) {
        super(mapper, rep);
    }
}
