package com.example.discussion.services;

import com.example.discussion.exceptions.DeleteException;
import com.example.discussion.exceptions.NotFoundException;
import com.example.discussion.exceptions.UpdateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CrudService <RQ, RP> {
    RP getById(@Min(0) Long id) throws NotFoundException;

    List<RP> getAll();

    RP save(@Valid RQ requestTo, String country);

    void delete(@Min(0) Long id) throws DeleteException;

    RP update(@Valid RQ requestTo, String country) throws UpdateException;
}
