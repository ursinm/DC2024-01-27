package by.bsuir.springapi.controller;

import by.bsuir.springapi.model.request.CreatorRequestTo;
import by.bsuir.springapi.model.request.IssueRequestTo;
import by.bsuir.springapi.model.response.CreatorResponseTo;
import by.bsuir.springapi.model.response.IssueResponseTo;
import by.bsuir.springapi.service.IssueService;
import by.bsuir.springapi.service.RestService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/issues")
@Data
@RequiredArgsConstructor
public class IssueController {

    private final RestService<IssueRequestTo, IssueResponseTo> issueService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponseTo> findAll() {
        return issueService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueResponseTo create(@Valid @RequestBody IssueRequestTo dto) {
        return issueService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo get(@Valid @PathVariable("id") Long id) {
        return issueService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo update(@Valid @RequestBody IssueRequestTo dto) {
        return issueService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        issueService.removeById(id);
    }
}
