package com.luschickij.publisher.repository;

import com.luschickij.publisher.dto.creator.CreatorRequestTo;
import com.luschickij.publisher.model.Creator;
import org.springframework.stereotype.Component;

@Component
public interface CreatorRepository extends ICommonRepository<Creator, Long> {
}
