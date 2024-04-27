package by.denisova.jpa.controller;

import by.denisova.jpa.BaseIntegrationTest;
import by.denisova.jpa.dto.request.CreateCommentDto;
import by.denisova.jpa.dto.request.UpdateCommentDto;
import by.denisova.jpa.dto.response.CommentResponseDto;
import by.denisova.jpa.util.dto.comment.request.CreateCommentDtoTestBuilder;
import by.denisova.jpa.util.dto.comment.request.UpdateCommentDtoTestBuilder;
import by.denisova.jpa.util.dto.comment.response.CreateCommentResponseTestBuilder;
import by.denisova.jpa.util.dto.comment.response.CommentResponseDtoTestBuilder;
import by.denisova.jpa.util.dto.comment.response.UpdateCommentResponseTestBuilder;
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
@DisplayName("Tests for CommentController")
class CommentControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Nested
    class FindCommentByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CommentResponseDto expected = CommentResponseDtoTestBuilder.note().build();

            mockMvc.perform(get("/api/v1.0/notes/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CommentResponseDto note = CommentResponseDtoTestBuilder.note().build();
            final List<CommentResponseDto> expected = List.of(note);

            mockMvc.perform(get("/api/v1.0/notes", note.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateCommentDto request = CreateCommentDtoTestBuilder.note().build();
            CommentResponseDto expected = CreateCommentResponseTestBuilder.note().build();

            mockMvc.perform(post("/api/v1.0/notes")
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
            mockMvc.perform(delete("/api/v1.0/notes/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateCommentDto request = UpdateCommentDtoTestBuilder.note().build();
            CommentResponseDto expected = UpdateCommentResponseTestBuilder.note().build();

            mockMvc.perform(put("/api/v1.0/notes")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

    }
}