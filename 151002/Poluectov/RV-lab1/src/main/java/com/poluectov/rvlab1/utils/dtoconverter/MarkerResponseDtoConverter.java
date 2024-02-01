package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.marker.MarkerResponseTo;
import com.poluectov.rvlab1.model.Marker;
import org.springframework.stereotype.Component;

@Component
public class MarkerResponseDtoConverter implements DtoConverter<Marker, MarkerResponseTo> {

    @Override
    public MarkerResponseTo convert(Marker marker) {
        return MarkerResponseTo.builder()
                .id(marker.getId())
                .name(marker.getName())
                .build();
    }
}
