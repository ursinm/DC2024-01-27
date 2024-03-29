package com.example.storyteller.service;

import com.example.storyteller.dto.requestDto.CreatorRequestTo;
import com.example.storyteller.dto.responseDto.CreatorResponseTo;
import com.example.storyteller.model.Creator;

public interface CreatorService {

    CreatorResponseTo create(CreatorRequestTo dto);

    Iterable<CreatorResponseTo> findAllDtos();

    CreatorResponseTo findDtoById(Long id);

    Creator findCreatorById(Long id);

    CreatorResponseTo update(CreatorRequestTo dto);

    void delete(Long id);
}
