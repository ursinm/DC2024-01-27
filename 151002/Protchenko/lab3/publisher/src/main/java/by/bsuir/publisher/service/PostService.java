package by.bsuir.publisher.service;

import by.bsuir.publisher.model.dto.request.PostRequestDto;
import by.bsuir.publisher.model.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final RestTemplate restTemplate;
    private final String URL = "http://localhost:24130/api/v1.0/posts";

    public List<PostResponseDto> getAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostRequestDto[]> entity = new HttpEntity<>(headers);
        ResponseEntity<PostResponseDto[]> response = restTemplate.exchange(URL, HttpMethod.GET, entity, PostResponseDto[].class);
        return new ArrayList<>(List.of(Objects.requireNonNull(response.getBody())));
    }

    public PostResponseDto get(Long id) {
        String getURL = String.format(Locale.ENGLISH, "%s/%d", URL, id);
        ResponseEntity<PostResponseDto> response = restTemplate.exchange(getURL, HttpMethod.GET, null, PostResponseDto.class);
        return Objects.requireNonNull(response.getBody());
    }

    public PostResponseDto create(PostRequestDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostRequestDto> entity = new HttpEntity<>(dto, headers);
        ResponseEntity<PostResponseDto> response = restTemplate.exchange(URL, HttpMethod.POST, entity, PostResponseDto.class);
        return response.getBody();
    }

    public PostResponseDto update(PostRequestDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PostRequestDto> entity = new HttpEntity<>(dto, headers);
        ResponseEntity<PostResponseDto> response = restTemplate.exchange(URL, HttpMethod.PUT, entity, PostResponseDto.class);
        return response.getBody();
    }

    public void delete(Long id) {
        String getURL = String.format(Locale.ENGLISH, "%s/%d", URL, id);
        ResponseEntity<PostResponseDto> response = restTemplate.exchange(getURL, HttpMethod.DELETE, null, PostResponseDto.class);
    }
}
