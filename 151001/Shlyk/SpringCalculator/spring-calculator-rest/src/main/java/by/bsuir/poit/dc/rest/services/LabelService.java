package by.bsuir.poit.dc.rest.services;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateLabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
public interface LabelService {
    void create(@Valid UpdateLabelDto dto);

    void update(long labelId, @Valid UpdateLabelDto dto);

    LabelDto getById(long labelId);

    LabelDto getByName(String name);

    List<LabelDto> getAll();

    void delete(long labelId);
}
