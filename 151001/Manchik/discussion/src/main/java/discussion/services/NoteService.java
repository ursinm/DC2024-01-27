package discussion.services;

import discussion.dto.NoteRequestTo;
import discussion.dto.NoteResponseTo;
import discussion.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService extends CrudService<NoteRequestTo, NoteResponseTo>{
    List<NoteResponseTo> getByStoryId(Long storyId) throws NotFoundException;
}