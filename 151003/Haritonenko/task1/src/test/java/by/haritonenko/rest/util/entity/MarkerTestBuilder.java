package by.haritonenko.rest.util.entity;

import by.haritonenko.rest.model.Marker;
import by.harlap.rest.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "marker")
@With
public class MarkerTestBuilder implements TestBuilder<Marker> {

    private Long id = 1L;
    private String name = "markerName";

    @Override
    public Marker build() {
        Marker marker = new Marker();

        marker.setId(id);
        marker.setName(name);

        return marker;
    }
}
