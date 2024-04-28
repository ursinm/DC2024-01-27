package by.denisova.jpa.controller;

import by.denisova.jpa.BaseIntegrationTest;
import by.denisova.jpa.dto.request.CreateMarkerDto;
import by.denisova.jpa.dto.request.UpdateMarkerDto;
import by.denisova.jpa.dto.response.MarkerResponseDto;
import by.denisova.jpa.util.dto.marker.request.CreateMarkerDtoTestBuilder;
import by.denisova.jpa.util.dto.marker.request.UpdateMarkerDtoTestBuilder;
import by.denisova.jpa.util.dto.marker.response.CreateMarkerResponseTestBuilder;
import by.denisova.jpa.util.dto.marker.response.MarkerResponseDtoTestBuilder;
import by.denisova.jpa.util.dto.marker.response.UpdateMarkerResponseTestBuilder;
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
@DisplayName("Tests for MarkerController")
class MarkerControllerTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Nested
    class FindMarkerByIdTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            MarkerResponseDto expected = MarkerResponseDtoTestBuilder.marker().build();

            mockMvc.perform(get("/api/v1.0/markers/{id}", expected.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            MarkerResponseDto marker = MarkerResponseDtoTestBuilder.marker().build();
            final List<MarkerResponseDto> expected = List.of(marker);

            mockMvc.perform(get("/api/v1.0/markers", marker.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            CreateMarkerDto request = CreateMarkerDtoTestBuilder.marker().build();
            MarkerResponseDto expected = CreateMarkerResponseTestBuilder.marker().build();

            mockMvc.perform(post("/api/v1.0/markers")
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
            mockMvc.perform(delete("/api/v1.0/markers/{id}", id))
                    .andExpect(status().isNoContent());
        }

    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() throws Exception {
            UpdateMarkerDto request = UpdateMarkerDtoTestBuilder.marker().build();
            MarkerResponseDto expected = UpdateMarkerResponseTestBuilder.marker().build();

            mockMvc.perform(put("/api/v1.0/markers")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

    }
}