package com.luschickij.publisher.repository.http;

import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.repository.PostRepository;
import com.luschickij.publisher.repository.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class HttpPostRepository implements PostRepository {

    WebClient.Builder webClientBuilder;

    String url;

    @Autowired
    public HttpPostRepository(
            WebClient.Builder webClientBuilder,
            @Value("${url.post-service}") String url) {
        this.webClientBuilder = webClientBuilder;
        this.url = url;
    }


    @Override
    public List<Post> findAll() throws EntityNotFoundException {
        Optional<Post[]> posts = webClientBuilder.build()
                .get()
                .uri(url + "/posts")
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    if (clientResponse.statusCode().value() == 404) {
                        throw new EntityNotFoundException("Posts not found");
                    }
                    throw new RuntimeException();
                })
                .bodyToMono(Post[].class)
                .blockOptional(Duration.ofSeconds(10));
        if (posts.isEmpty()) {
            throw new RuntimeException("Response took too long");
        }
        return Arrays.stream(posts.get()).toList();
    }

    @Override
    public Post save(Post entity) throws EntityNotFoundException {

        Optional<Post> post = webClientBuilder.build()
                .post()
                .uri(url + "/posts")
                .body(Mono.just(entity), PostRequestTo.class)
                .retrieve()
                .bodyToMono(Post.class)
                .blockOptional(Duration.ofSeconds(10));

        if (post.isPresent()) {
            return post.get();
        }
        throw new EntityNotFoundException("Post not found");
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/posts/{id}").buildAndExpand(id).toUri();

        Optional<ResponseEntity<Void>> post = webClientBuilder.build()
                .delete()
                .uri(uri)
                .retrieve()
                .toBodilessEntity()
                .blockOptional(Duration.ofSeconds(10));

        if (post.isEmpty()) {
            throw new RuntimeException("Response took too long");
        }
    }

    @Override
    public Optional<Post> findById(Long id) throws EntityNotFoundException {
        Optional<Post> post = webClientBuilder.build()
                .get()
                .uri(url + "/posts/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    if (clientResponse.statusCode().value() == 404) {
                        throw new EntityNotFoundException("Post not found");
                    }
                    throw new RuntimeException();
                })
                .bodyToMono(Post.class)
                .blockOptional(Duration.ofSeconds(10));

        if (post.isPresent()) {
            return post;
        }
        throw new RuntimeException("Response took too long");
    }

    @Override
    public Post update(Post post) {
        Optional<Post> updated = webClientBuilder.build()
                .put()
                .uri(url + "/posts")
                .body(Mono.just(post), PostRequestTo.class)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    if (clientResponse.statusCode().value() == 404) {
                        throw new EntityNotFoundException("Post not found");
                    }
                    throw new RuntimeException();
                })
                .bodyToMono(Post.class)
                .blockOptional(Duration.ofSeconds(10));

        if (updated.isPresent()) {
            return updated.get();
        }
        throw new RuntimeException("Response took too long");
    }
}
