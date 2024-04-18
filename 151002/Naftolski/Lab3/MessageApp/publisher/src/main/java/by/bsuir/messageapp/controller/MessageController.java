package by.bsuir.messageapp.controller;

import by.bsuir.messageapp.util.HeadersUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("${app.messages-path}")
@RequiredArgsConstructor
public class MessageController {
    private final RestClient restClient;

    @Value("${app.messages-path}")
    private String messagesPath;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader HttpHeaders headers) {
        return restClient.get()
                .uri(messagesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader HttpHeaders headers,
                                    @RequestBody Object o) {

        return restClient.post()
                .uri(messagesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .body(o)
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@RequestHeader HttpHeaders headers,
                                 @PathVariable Map<String, Object> pathVars) {
        return restClient.get()
                .uri(messagesPath + "/{id}", pathVars)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader HttpHeaders headers,
                                    @RequestBody Object o) {
        return restClient.put()
                .uri(messagesPath)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .body(o)
                .retrieve()
                .toEntity(String.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader HttpHeaders headers,
                                    @PathVariable Map<String, Object> pathVars) {
        return restClient.delete()
                .uri(messagesPath + "/{id}", pathVars)
                .headers(httpHeaders -> HeadersUtil.AddHeadersWithoutLength(httpHeaders, headers))
                .retrieve()
                .toEntity(String.class);
    }
}
