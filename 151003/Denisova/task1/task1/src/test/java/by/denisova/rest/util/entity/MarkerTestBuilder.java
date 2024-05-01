package by.denisova.rest.util.entity;

import by.denisova.rest.model.Marker;
import by.denisova.rest.util.TestBuilder;
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
