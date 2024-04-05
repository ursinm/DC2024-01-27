package com.example.restapplication.services;

import com.example.restapplication.exceptions.DeleteException;
import com.example.restapplication.exceptions.NotFoundException;
import com.example.restapplication.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CrudService <RQ, RP>{

    RP getById(@Min(0) Long id) throws NotFoundException;

    List<RP> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    RP save(@Valid RQ requestTo);

    void delete(@Min(0) Long id) throws DeleteException;

    RP update(@Valid RQ requestTo) throws UpdateException;
}
