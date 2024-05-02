package com.example.distributedcomputing.mapper;

import com.example.distributedcomputing.model.entity.Editor;
import com.example.distributedcomputing.model.request.EditorRequestTo;
import com.example.distributedcomputing.model.response.EditorResponseTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EditorMapper {
    Editor dtoToEntity(EditorRequestTo editorRequestTo);
    List<Editor> dtoToEntity(Iterable<Editor> editors);

    EditorResponseTo entityToDto(Editor editor);

    List<EditorResponseTo> entityToDto(Iterable<Editor> editors);
}
