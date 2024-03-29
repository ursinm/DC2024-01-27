package by.bsuir.dc.features.marker;

import by.bsuir.dc.features.marker.dto.MarkerRequestDto;
import by.bsuir.dc.features.marker.dto.MarkerResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/markers")
public class MarkerController {
    private final MarkerService markerService;

    @PostMapping
    public ResponseEntity<MarkerResponseDto> addMarker(@RequestBody MarkerRequestDto markerRequestDto) {
        var marker = markerService.addMarker(markerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(marker);
    }

    @GetMapping
    public ResponseEntity<List<MarkerResponseDto>> getAllMarkers() {
        var markers = markerService.getAllMarkers();
        return ResponseEntity.ok(markers);
    }

    @GetMapping("/{markerId}")
    public ResponseEntity<MarkerResponseDto> getById(@PathVariable Long markerId) {
        var marker = markerService.getById(markerId);
        return ResponseEntity.ok(marker);
    }

    @PutMapping("/{markerId}")
    public ResponseEntity<MarkerResponseDto> update(
            @PathVariable Long markerId,
            @RequestBody MarkerRequestDto markerRequestDto
    ) {
        var marker = markerService.updateById(markerId, markerRequestDto);
        return ResponseEntity.ok(marker);
    }

    @DeleteMapping("/{markerId}")
    public ResponseEntity<?> deleteById(@PathVariable Long markerId) {
        markerService.deleteById(markerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
