package by.haritonenko.jpa.util.dto.marker.response;

import by.haritonenko.jpa.dto.response.MarkerResponseDto;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class CreateMarkerResponseTestBuilder implements TestBuilder<MarkerResponseDto> {

    private Long id = 2L;
    private String name = "name";

    @Override
    public MarkerResponseDto build() {
        MarkerResponseDto marker = new MarkerResponseDto();

        marker.setId(id);
        marker.setName(name);

        return marker;
    }
}