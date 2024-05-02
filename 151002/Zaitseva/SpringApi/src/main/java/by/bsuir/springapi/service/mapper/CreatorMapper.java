package by.bsuir.springapi.service.mapper;

import by.bsuir.springapi.model.entity.Creator;
import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
//    EditorRequestTo getRequestTo(Editor editor);
//
//    List<EditorRequestTo> getListRequestTo(Iterable<Editor> editors);

    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    CreatorResponseTo getResponseTo(Creator creator);

    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    List<CreatorResponseTo> getListResponseTo(Iterable<Creator> editors);

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    Creator getEditor(CreatorRequestTo creatorRequestTo);

//    List<Editor> getEditors(Iterable<EditorRequestTo> editorRequestTos);
}
