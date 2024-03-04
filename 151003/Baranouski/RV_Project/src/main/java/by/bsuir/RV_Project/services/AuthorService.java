package by.bsuir.RV_Project.services;

import by.bsuir.RV_Project.dto.requests.AuthorRequestDto;
import by.bsuir.RV_Project.dto.responses.AuthorResponseDto;

import java.util.List;

public interface AuthorService extends BaseService<AuthorRequestDto, AuthorResponseDto> {
    List<AuthorResponseDto> readAll();
}
