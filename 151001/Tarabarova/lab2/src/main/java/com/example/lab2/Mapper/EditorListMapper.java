package com.example.lab2.Mapper;

import com.example.lab2.DTO.EditorRequestTo;
import com.example.lab2.DTO.EditorResponseTo;
import com.example.lab2.Model.Editor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EditorMapper.class)
public interface EditorListMapper {
    List<Editor> toEditorList(List<EditorRequestTo> editors);

    List<EditorResponseTo> toEditorResponseList(List<Editor> editors);
}
