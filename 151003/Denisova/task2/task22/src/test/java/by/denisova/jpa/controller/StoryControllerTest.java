package by.denisova.jpa.controller;

import by.denisova.jpa.BaseIntegrationTest;
import by.denisova.jpa.dto.request.CreateStoryDto;
import by.denisova.jpa.dto.request.UpdateStoryDto;
import by.denisova.jpa.dto.response.StoryResponseDto;
import by.denisova.jpa.util.dto.story.request.CreateStoryDtoTestBuilder;
import by.denisova.jpa.util.dto.story.request.UpdateStoryDtoTestBuilder;
import by.denisova.jpa.util.dto.story.response.StoryResponseDtoTestBuilder;
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
@DisplayName("Tests for StoryController")
class StoryControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    
    @Nested
    class FindStoryByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            StoryResponseDto expected = StoryResponseDtoTestBuilder.story().build();

            mockMvc.perform(get("/api/v1.0/storys/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            StoryResponseDto story = StoryResponseDtoTestBuilder.story().build();
            final List<StoryResponseDto> expected = List.of(story);

            mockMvc.perform(get("/api/v1.0/storys", story.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateStoryDto request = CreateStoryDtoTestBuilder.story().build();

            mockMvc.perform(post("/api/v1.0/storys")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class DeleteTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            final Long id = 1L;
            mockMvc.perform(delete("/api/v1.0/storys/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateStoryDto request = UpdateStoryDtoTestBuilder.story().build();

            mockMvc.perform(put("/api/v1.0/storys")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

    }
}