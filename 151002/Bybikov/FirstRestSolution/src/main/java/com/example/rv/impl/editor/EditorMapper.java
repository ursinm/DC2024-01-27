package com.example.rv.impl.editor;

import java.util.List;

public interface EditorMapper {

    EditorRequestTo editorToRequestTo(Editor editor);

    List<EditorRequestTo> editorToRequestTo(Iterable<Editor> editors);

    Editor dtoToEntity(EditorRequestTo editorRequestTo);

    List<Editor> dtoToEntity(Iterable<EditorRequestTo> editorRequestTos);

    EditorResponseTo editorToResponseTo(Editor editor);

    List<EditorResponseTo> editorToResponseTo(Iterable<Editor> editors);
    
    
}
