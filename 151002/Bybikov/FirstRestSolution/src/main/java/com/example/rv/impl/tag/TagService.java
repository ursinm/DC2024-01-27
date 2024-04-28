package com.example.rv.impl.tag;

import com.example.rv.api.repository.CrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {

    public final CrudRepository<Tag, Long> tagCrudRepository;

    public final TagMapperImpl tagMapper;
}
