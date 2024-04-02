package by.harlap.jpa.controller;

import by.harlap.jpa.BaseIntegrationTest;
import by.harlap.jpa.dto.request.CreateAuthorDto;
import by.harlap.jpa.dto.request.UpdateAuthorDto;
import by.harlap.jpa.dto.response.AuthorResponseDto;
import by.harlap.jpa.util.dto.author.request.CreateAuthorDtoTestBuilder;
import by.harlap.jpa.util.dto.author.request.UpdateAuthorDtoTestBuilder;
import by.harlap.jpa.util.dto.author.response.AuthorResponseDtoTestBuilder;
import by.harlap.jpa.util.dto.author.response.CreateAuthorResponseDtoTestBuilder;
import by.harlap.jpa.util.dto.author.response.UpdateAuthorResponseTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@DisplayName("Tests for AuthorController")
class AuthorControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Nested
    class FindAuthorByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            AuthorResponseDto expected = AuthorResponseDtoTestBuilder.author().build();

            mockMvc.perform(get("/api/v1.0/authors/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            AuthorResponseDto author = AuthorResponseDtoTestBuilder.author().build();
            final List<AuthorResponseDto> expected = List.of(author);

            mockMvc.perform(get("/api/v1.0/authors", author.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateAuthorDto request = CreateAuthorDtoTestBuilder.author().build();
            AuthorResponseDto expected = CreateAuthorResponseDtoTestBuilder.author().build();

            mockMvc.perform(post("/api/v1.0/authors")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class DeleteTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            final Long id = 1L;
            mockMvc.perform(delete("/api/v1.0/authors/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateAuthorDto request = UpdateAuthorDtoTestBuilder.author().build();
            AuthorResponseDto expected = UpdateAuthorResponseTestBuilder.author().build();

            mockMvc.perform(put("/api/v1.0/authors")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

    }
}