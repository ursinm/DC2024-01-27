package by.haritonenko.rest.util.dto.response;

import by.haritonenko.rest.dto.response.MarkerResponseDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class MarkerResponseDtoTestBuilder implements TestBuilder<MarkerResponseDto> {

    private Long id = 1L;
    private String name = "markerName";

    @Override
    public MarkerResponseDto build() {
        MarkerResponseDto marker = new MarkerResponseDto();

        marker.setId(id);
        marker.setName(name);

        return marker;
    }
}
