package by.rusakovich.publisher.controller;

import by.rusakovich.publisher.model.dto.author.AuthorRequestTO;
import by.rusakovich.publisher.model.dto.author.AuthorResponseTO;
import by.rusakovich.publisher.service.impl.AuthorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.url}/authors")
public class AuthorController extends Controller<Long, AuthorRequestTO, AuthorResponseTO, AuthorService> {
    public AuthorController(AuthorService service) {
        super(service);
    }
}
