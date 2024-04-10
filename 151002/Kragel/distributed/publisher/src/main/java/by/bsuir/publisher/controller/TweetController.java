package by.bsuir.publisher.controller;

import by.bsuir.publisher.dto.request.TweetRequestDto;
import by.bsuir.publisher.dto.response.TweetResponseDto;
import by.bsuir.publisher.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public ResponseEntity<List<TweetResponseDto>> getAll() {
        return ResponseEntity.ok(tweetService.getAll());
    }

    @PostMapping
    public ResponseEntity<TweetResponseDto> create(@Valid @RequestBody TweetRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tweetService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseDto> get(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(tweetService.getById(id));
    }

    @PutMapping
    public ResponseEntity<TweetResponseDto> update(@Valid @RequestBody TweetRequestDto dto) {
        return ResponseEntity.ok(tweetService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        tweetService.deleteById(id);
    }
}
