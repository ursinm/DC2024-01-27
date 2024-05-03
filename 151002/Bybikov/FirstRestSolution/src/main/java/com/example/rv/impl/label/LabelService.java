package com.example.rv.impl.label;

import com.example.rv.api.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelService {

    public final CrudRepository<Label, Long> tagCrudRepository;

    public final LabelMapperImpl tagMapper;
}
