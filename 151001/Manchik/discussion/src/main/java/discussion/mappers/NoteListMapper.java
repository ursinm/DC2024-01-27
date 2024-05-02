package discussion.mappers;

import discussion.dto.NoteRequestTo;
import discussion.dto.NoteResponseTo;
import discussion.entities.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = NoteMapper.class)
public interface NoteListMapper {
    List<Note> toNoteList(List<NoteRequestTo> notes);
    List<NoteResponseTo> toNoteResponseList(List<Note> notes);
}
