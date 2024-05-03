package com.example.rv.impl.label;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LabelMapperImpl implements LabelMapper {
    @Override
    public LabelRequestTo tagToRequestTo(Label label) {
        return new LabelRequestTo(
                label.getId(),
                label.getName()
        );
    }

    @Override
    public List<LabelRequestTo> tagToRequestTo(Iterable<Label> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Label dtoToEntity(LabelRequestTo labelRequestTo) {
        return new Label(
                labelRequestTo.id(),
                labelRequestTo.name()
        );
    }

    @Override
    public List<Label> dtoToEntity(Iterable<LabelRequestTo> tagRequestTos) {
        return StreamSupport.stream(tagRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public LabelResponseTo tagToResponseTo(Label label) {
        return new LabelResponseTo(
                label.getId(),
                label.getName()
        );
    }

    @Override
    public List<LabelResponseTo> tagToResponseTo(Iterable<Label> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToResponseTo)
                .collect(Collectors.toList());
    }
}
