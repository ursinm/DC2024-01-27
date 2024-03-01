package by.bsuir.dc.rest_basics.services.impl.mappers;

import by.bsuir.dc.rest_basics.entities.Label;
import by.bsuir.dc.rest_basics.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.LabelResponseTo;
import org.mapstruct.Mapper;

@Mapper
public interface LabelMapper
        extends EntityMapper<LabelRequestTo, LabelResponseTo, Label> {
}
