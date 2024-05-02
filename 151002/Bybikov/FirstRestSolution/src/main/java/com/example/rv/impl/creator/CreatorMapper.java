package com.example.rv.impl.creator;

import java.util.List;

public interface CreatorMapper {

    CreatorRequestTo editorToRequestTo(Creator creator);

    List<CreatorRequestTo> editorToRequestTo(Iterable<Creator> editors);

    Creator dtoToEntity(CreatorRequestTo creatorRequestTo);

    List<Creator> dtoToEntity(Iterable<CreatorRequestTo> editorRequestTos);

    CreatorResponseTo editorToResponseTo(Creator creator);

    List<CreatorResponseTo> editorToResponseTo(Iterable<Creator> editors);
    
    
}
