package com.example.rv.impl.label;
import java.util.List;

public interface LabelMapper {
    LabelRequestTo tagToRequestTo(Label label);

    List<LabelRequestTo> tagToRequestTo(Iterable<Label> tags);

    Label dtoToEntity(LabelRequestTo labelRequestTo);

    List<Label> dtoToEntity(Iterable<LabelRequestTo> tagRequestTos);

    LabelResponseTo tagToResponseTo(Label label);

    List<LabelResponseTo> tagToResponseTo(Iterable<Label> tags);
}
