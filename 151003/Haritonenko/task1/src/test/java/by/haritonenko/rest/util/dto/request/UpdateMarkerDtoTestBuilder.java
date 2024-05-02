package by.haritonenko.rest.util.dto.request;

import by.haritonenko.rest.dto.request.UpdateMarkerDto;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class UpdateMarkerDtoTestBuilder implements TestBuilder<UpdateMarkerDto> {

    private Long id = 1L;
    private String name = "markerName";

    @Override
    public UpdateMarkerDto build() {
        return new UpdateMarkerDto(id, name);
    }
}