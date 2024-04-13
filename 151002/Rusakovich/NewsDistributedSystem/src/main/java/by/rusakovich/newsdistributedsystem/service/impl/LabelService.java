package by.rusakovich.newsdistributedsystem.service.impl;

import by.rusakovich.newsdistributedsystem.dao.IEntityRepository;
import by.rusakovich.newsdistributedsystem.model.dto.label.LabelRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.label.LabelResponseTO;
import by.rusakovich.newsdistributedsystem.model.dto.mapper.EntityMapper;
import by.rusakovich.newsdistributedsystem.model.entity.impl.Label;
import by.rusakovich.newsdistributedsystem.service.EntityService;
import org.springframework.stereotype.Service;

@Service
public class LabelService extends EntityService<Long, LabelRequestTO, LabelResponseTO, Label<Long>> {
    public LabelService(EntityMapper<Long, Label<Long>, LabelRequestTO, LabelResponseTO> mapper, IEntityRepository<Long, Label<Long>> rep) {
        super(mapper, rep);
    }
}
