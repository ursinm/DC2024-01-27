package by.denisova.rest.util.dto.request;

import by.denisova.rest.dto.request.CreateMarkerDto;
import by.denisova.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class CreateMarkerDtoTestBuilder implements TestBuilder<CreateMarkerDto> {

    private String name = "markerName";

    @Override
    public CreateMarkerDto build() {
        return new CreateMarkerDto(name);
    }
}
