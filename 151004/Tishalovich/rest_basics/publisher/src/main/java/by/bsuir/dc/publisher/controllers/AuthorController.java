package by.bsuir.dc.publisher.controllers;

import by.bsuir.dc.publisher.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.publisher.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.publisher.services.common.AbstractService;
import by.bsuir.dc.publisher.controllers.common.AbstractController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/authors")
public class AuthorController extends AbstractController<AuthorRequestTo, AuthorResponseTo> {

    public AuthorController(AbstractService<AuthorRequestTo, AuthorResponseTo> service) {
        super(service);
    }

}
