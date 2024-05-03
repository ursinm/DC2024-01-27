package com.example.publisher.controller;

import com.example.publisher.model.request.LabelRequestTo;
import com.example.publisher.model.response.LabelResponseTo;
import com.example.publisher.service.LabelService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/labels")
@Data
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelResponseTo findById(@Valid @PathVariable("id") Long id) {
        return labelService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelResponseTo> findAll() {
        return labelService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelResponseTo create(@Valid @RequestBody LabelRequestTo request) {
        return labelService.create(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public LabelResponseTo update(@Valid @RequestBody LabelRequestTo request) {
        return labelService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean removeById(@Valid @PathVariable("id") Long id) {
        return labelService.removeById(id);
    }
}
