package com.example.rv.impl.editor;

import com.example.rv.api.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditorService {

    public final CrudRepository<Editor, Long> editorCrudRepository;

    public final EditorMapperImpl editorMapper;
}
