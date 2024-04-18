package by.rusakovich.publisher.controller;

import by.rusakovich.publisher.model.dto.note.NoteRequestTO;
import by.rusakovich.publisher.model.dto.note.NoteResponseTO;
import by.rusakovich.publisher.service.impl.NoteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.url}/notes")
public class NoteController extends Controller<Long, NoteRequestTO, NoteResponseTO, NoteService> {

    public NoteController(NoteService service) {
        super(service);
    }
}
