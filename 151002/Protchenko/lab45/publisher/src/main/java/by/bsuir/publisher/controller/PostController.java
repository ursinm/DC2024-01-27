package by.bsuir.publisher.controller;

import by.bsuir.publisher.model.dto.request.PostRequestDto;
import by.bsuir.publisher.model.dto.response.PostResponseDto;
import by.bsuir.publisher.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1.0/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@Valid @PathVariable("id") Long id) {
            return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@Valid @RequestBody PostRequestDto dto,
                                                  HttpServletRequest request
    ) {
        final Locale locale = request.getLocale();
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(dto, locale));
    }

    @PutMapping
    public ResponseEntity<PostResponseDto> update(@Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.update(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable("id") Long id) {
        postService.deleteById(id);
    }
}
