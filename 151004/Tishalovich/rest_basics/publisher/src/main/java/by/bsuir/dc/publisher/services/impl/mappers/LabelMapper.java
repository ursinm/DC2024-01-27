package by.bsuir.dc.publisher.services.impl.mappers;

import by.bsuir.dc.publisher.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.LabelResponseTo;
import by.bsuir.dc.publisher.entities.Label;
import org.mapstruct.Mapper;

@Mapper
public interface LabelMapper
        extends EntityMapper<LabelRequestTo, LabelResponseTo, Label> {
}
