package by.haritonenko.rest.service.impl;

import by.haritonenko.rest.exception.EntityNotFoundException;
import by.haritonenko.rest.repository.AbstractRepository;
import by.haritonenko.rest.model.Marker;
import by.haritonenko.rest.util.entity.MarkerTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkerServiceImplTest {

    @InjectMocks
    private MarkerServiceImpl markerService;

    @Mock
    private AbstractRepository<Marker, Long> markerRepository;

    @Nested
    class SaveTest {

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Marker expected = MarkerTestBuilder.marker().build();

            doReturn(expected)
                    .when(markerRepository)
                    .save(expected);

            final Marker actual = markerService.save(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long markerId = 1L;
            final String expectedMessage = MarkerServiceImpl.MARKER_NOT_FOUND_MESSAGE.formatted(markerId);

            Mockito.doThrow(new EntityNotFoundException(expectedMessage))
                    .when(markerRepository)
                    .findById(markerId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> markerService.findById(markerId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Marker expected = MarkerTestBuilder.marker().build();

            doReturn(Optional.of(expected))
                    .when(markerRepository)
                    .findById(expected.getId());

            final Marker actual = markerService.findById(expected.getId());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("Test should return a list that contains expected value")
        void testShouldReturnListOfSizeOne() {
            final Marker marker = MarkerTestBuilder.marker().build();
            final List<Marker> markerList = List.of(marker);

            doReturn(markerList)
                    .when(markerRepository)
                    .findAll();

            final List<Marker> actual = markerService.findAll();

            assertThat(actual).contains(marker);
        }
    }

    @Nested
    class DeleteByIdTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Long markerId = 1L;
            final String expectedMessage = MarkerServiceImpl.MARKER_NOT_FOUND_MESSAGE.formatted(markerId);

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(markerRepository)
                    .findById(markerId);

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> markerService.deleteById(markerId));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should complete successfully for any provided id (both existing and no)")
        void testShouldCompleteForExistingEntity() {
            final Long markerId = 1L;
            final Marker marker = MarkerTestBuilder.marker().build();

            doReturn(Optional.of(marker))
                    .when(markerRepository)
                    .findById(markerId);

            doNothing().when(markerRepository)
                    .deleteById(markerId);

            markerService.deleteById(markerId);

            verify(markerRepository, times(1)).deleteById(markerId);
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("Test should throw EntityNotFoundException with expected message")
        void testShouldThrowEntityNotFoundException() {
            final Marker marker = MarkerTestBuilder.marker().build();
            final String expectedMessage = MarkerServiceImpl.MARKER_NOT_FOUND_MESSAGE.formatted(marker.getId());

            doThrow(new EntityNotFoundException(expectedMessage))
                    .when(markerRepository)
                    .findById(marker.getId());

            final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> markerService.update(marker));
            final String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        @DisplayName("Test should return expected response")
        void testShouldReturnExpectedResponse() {
            final Marker expected = MarkerTestBuilder.marker().build();

            doReturn(Optional.of(expected))
                    .when(markerRepository)
                    .findById(expected.getId());

            doReturn(expected)
                    .when(markerRepository)
                    .update(expected);

            final Marker actual = markerService.update(expected);

            assertThat(actual).isEqualTo(expected);
        }
    }

}
