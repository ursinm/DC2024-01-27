package com.example.lab1.Mapper;

import com.example.lab1.DTO.EditorRequestTo;
import com.example.lab1.DTO.EditorResponseTo;
import com.example.lab1.Model.Editor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = EditorMapper.class)
public interface EditorListMapper {
    List<Editor> toEditorList(List<EditorRequestTo> editors);

    List<EditorResponseTo> toEditorResponseList(List<Editor> editors);
}
