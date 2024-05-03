package by.denisova.jpa.util.dto.marker.response;

import by.denisova.jpa.dto.response.MarkerResponseDto;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class UpdateMarkerResponseTestBuilder implements TestBuilder<MarkerResponseDto> {

    private Long id = 1L;
    private String name = "newMarkerName";

    @Override
    public MarkerResponseDto build() {
        MarkerResponseDto marker = new MarkerResponseDto();

        marker.setId(id);
        marker.setName(name);

        return marker;
    }
}