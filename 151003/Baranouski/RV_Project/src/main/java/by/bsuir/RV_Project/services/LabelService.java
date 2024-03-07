package by.bsuir.RV_Project.services;

import by.bsuir.RV_Project.dto.requests.LabelRequestDto;
import by.bsuir.RV_Project.dto.responses.LabelResponseDto;

import java.util.List;

public interface LabelService extends BaseService<LabelRequestDto, LabelResponseDto> {
    List<LabelResponseDto> readAll();
}
