package by.haritonenko.jpa.util.dto.marker.request;

import by.haritonenko.jpa.dto.request.CreateMarkerDto;
import by.haritonenko.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class CreateMarkerDtoTestBuilder implements TestBuilder<CreateMarkerDto> {

    private String name = "name";

    @Override
    public CreateMarkerDto build() {
        return new CreateMarkerDto(name);
    }
}
