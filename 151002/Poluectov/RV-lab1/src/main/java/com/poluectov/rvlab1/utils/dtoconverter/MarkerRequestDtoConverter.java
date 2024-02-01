package com.poluectov.rvlab1.utils.dtoconverter;

import com.poluectov.rvlab1.dto.marker.MarkerRequestTo;
import com.poluectov.rvlab1.model.Marker;
import org.springframework.stereotype.Component;

@Component
public class MarkerRequestDtoConverter implements DtoConverter<MarkerRequestTo, Marker> {

    @Override
    public Marker convert(MarkerRequestTo marker) {
        return Marker.builder()
                .id(marker.getId())
                .name(marker.getName())
                .build();
    }
}
