package com.luschickij.publisher.repository;

import com.luschickij.publisher.dto.label.LabelRequestTo;
import com.luschickij.publisher.model.Label;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public interface LabelRepository extends ICommonRepository<Label, Long> {

    List<Label> findByIdIn(List<Long> ids);
}
