package com.example.storyteller.service;

import com.example.storyteller.dto.requestDto.MarkerRequestTo;
import com.example.storyteller.dto.responseDto.MarkerResponseTo;
import com.example.storyteller.model.Marker;

public interface MarkerService {

    MarkerResponseTo create(MarkerRequestTo dto);

    Iterable<MarkerResponseTo> findAllDtos();

    MarkerResponseTo findDtoById(Long id);

    Marker findMarkerById(Long id);

    MarkerResponseTo update(MarkerRequestTo dto);

    void delete(Long id);
}
