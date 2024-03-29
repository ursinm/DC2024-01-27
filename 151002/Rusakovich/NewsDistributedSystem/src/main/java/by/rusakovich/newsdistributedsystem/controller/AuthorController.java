package by.rusakovich.newsdistributedsystem.controller;

import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.author.AuthorResponseTO;
import by.rusakovich.newsdistributedsystem.service.impl.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/authors")
public class AuthorController extends Controller<Long, AuthorRequestTO, AuthorResponseTO, AuthorService> {
    public AuthorController(AuthorService service) {
        super(service);
    }
}
