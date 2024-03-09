package by.bsuir.RV_Project.services;

import by.bsuir.RV_Project.dto.requests.StoryRequestDto;
import by.bsuir.RV_Project.dto.responses.StoryResponseDto;

import java.util.List;

public interface StoryService extends BaseService<StoryRequestDto, StoryResponseDto> {
    List<StoryResponseDto> readAll();
}
