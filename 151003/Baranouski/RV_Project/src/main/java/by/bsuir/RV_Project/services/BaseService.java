package by.bsuir.RV_Project.services;

import by.bsuir.RV_Project.dto.requests.BaseRequestDto;
import by.bsuir.RV_Project.dto.responses.BaseResponseDto;
import by.bsuir.RV_Project.exceptions.EntityExistsException;
import by.bsuir.RV_Project.exceptions.NoEntityExistsException;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.Optional;

public interface BaseService<T extends BaseRequestDto, V extends BaseResponseDto> {
    V create(@Valid @NonNull T dto) throws EntityExistsException;
    Optional<V> read(@NonNull Long id);
    V update(@Valid @NonNull T dto) throws NoEntityExistsException;
    Long delete(@NonNull Long id) throws NoEntityExistsException;
}
