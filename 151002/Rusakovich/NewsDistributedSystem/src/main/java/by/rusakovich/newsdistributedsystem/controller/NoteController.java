package by.rusakovich.newsdistributedsystem.controller;

import by.rusakovich.newsdistributedsystem.model.dto.note.NoteRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.note.NoteResponseTO;
import by.rusakovich.newsdistributedsystem.service.impl.NoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController extends Controller<Long, NoteRequestTO, NoteResponseTO, NoteService> {

    public NoteController(NoteService service) {
        super(service);
    }
}
