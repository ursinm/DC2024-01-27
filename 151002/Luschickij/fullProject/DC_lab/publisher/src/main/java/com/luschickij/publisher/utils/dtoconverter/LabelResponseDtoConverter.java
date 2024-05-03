package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.label.LabelResponseTo;
import com.luschickij.publisher.model.Label;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class LabelResponseDtoConverter implements DtoConverter<Label, LabelResponseTo> {

    @Override
    public LabelResponseTo convert(Label label) {
        return LabelResponseTo.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }
}
