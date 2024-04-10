package by.bsuir.dc.features.post;

import by.bsuir.dc.features.post.dto.PostResponseDto;
import by.bsuir.dc.features.post.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/posts")
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        var post = postService.addPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        var posts = postService.getAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        var post = postService.getById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto postRequestDto
    ) {
        var post = postService.update(postId, postRequestDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
        postService.deleteById(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
