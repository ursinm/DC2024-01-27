package com.luschickij.publisher.service;

import com.luschickij.publisher.dto.label.LabelRequestTo;
import com.luschickij.publisher.dto.label.LabelResponseTo;
import com.luschickij.publisher.model.Label;
import com.luschickij.publisher.repository.LabelRepository;
import com.luschickij.publisher.repository.jpa.JpaLabelRepository;
import com.luschickij.publisher.utils.dtoconverter.LabelRequestDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Optional;

@Component
public class LabelService extends CommonRestService<Label, LabelRequestTo, LabelResponseTo, Long> {
    public LabelService(
            @Qualifier("customJpaLabelRepository") LabelRepository repository,
            LabelRequestDtoConverter dtoConverter) {
        super(repository, dtoConverter);
    }

    @Override
    protected Optional<LabelResponseTo> mapResponseTo(Label label) {
        if (label == null) {
            return Optional.empty();
        }
        return Optional.of(LabelResponseTo.builder()
                .id(label.getId())
                .name(label.getName())
                .build());
    }

    @Override
    protected void update(Label one, Label found) {
        one.setName(found.getName());
    }
}
