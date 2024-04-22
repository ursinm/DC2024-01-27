package by.bsuir.restapi.controller;

import by.bsuir.restapi.model.dto.request.IssueRequestDto;
import by.bsuir.restapi.model.dto.response.IssueResponseDto;
import by.bsuir.restapi.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/issues")
public class IssueController {

    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<List<IssueResponseDto>> getAll() {
        return ResponseEntity.ok(issueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseDto> get(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(issueService.get(id));
    }

    @PostMapping
    public ResponseEntity<IssueResponseDto> create(@Valid @RequestBody IssueRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.create(dto));
    }

    @PutMapping
    public ResponseEntity<IssueResponseDto> update(@Valid @RequestBody IssueRequestDto dto) {
        return ResponseEntity.ok(issueService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable Long id) {
        issueService.delete(id);
    }
}
