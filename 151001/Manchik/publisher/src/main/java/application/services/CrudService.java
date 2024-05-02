package application.services;

import application.exceptions.DeleteException;
import application.exceptions.NotFoundException;
import application.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

public interface CrudService <RQ, RP>{

    RP getById(@Min(0) Long id) throws NotFoundException;

    List<RP> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    RP save(@Valid RQ requestTo);

    void delete(@Min(0) Long id) throws DeleteException;

    RP update(@Valid RQ requestTo) throws UpdateException;
}
