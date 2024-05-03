package com.luschickij.publisher.utils.dtoconverter;

import com.luschickij.publisher.dto.label.LabelRequestTo;
import com.luschickij.publisher.model.Label;
import org.springframework.stereotype.Component;

@Component
public class LabelRequestDtoConverter implements DtoConverter<LabelRequestTo, Label> {

    @Override
    public Label convert(LabelRequestTo label) {
        return Label.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }
}
