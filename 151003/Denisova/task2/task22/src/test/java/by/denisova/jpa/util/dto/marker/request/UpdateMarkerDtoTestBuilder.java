package by.denisova.jpa.util.dto.marker.request;

import by.denisova.jpa.dto.request.UpdateMarkerDto;
import by.denisova.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class UpdateMarkerDtoTestBuilder implements TestBuilder<UpdateMarkerDto> {

    private Long id = 1L;
    private String name = "newMarkerName";

    @Override
    public UpdateMarkerDto build() {
        return new UpdateMarkerDto(id, name);
    }
}