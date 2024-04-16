package by.bsuir.restapi.service;

import by.bsuir.restapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;


}
