package by.bsuir.dc.rest_basics.services.impl;

import by.bsuir.dc.rest_basics.dal.common.MemoryRepository;
import by.bsuir.dc.rest_basics.entities.Label;
import by.bsuir.dc.rest_basics.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.LabelResponseTo;
import by.bsuir.dc.rest_basics.services.LabelService;
import by.bsuir.dc.rest_basics.services.common.AbstractServiceImpl;
import by.bsuir.dc.rest_basics.services.impl.mappers.EntityMapper;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl extends AbstractServiceImpl<LabelRequestTo, LabelResponseTo, Label> implements LabelService {

    public LabelServiceImpl(MemoryRepository<Label> dao, EntityMapper<LabelRequestTo, LabelResponseTo, Label> mapper) {
        super(dao, mapper);
    }

}
