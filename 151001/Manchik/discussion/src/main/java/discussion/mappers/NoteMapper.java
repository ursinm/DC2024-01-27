package discussion.mappers;

import discussion.dto.NoteRequestTo;
import discussion.dto.NoteResponseTo;
import discussion.entities.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    Note toNote(NoteRequestTo note);
    NoteResponseTo toNoteResponse(Note note);
}
