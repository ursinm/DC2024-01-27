package com.poluectov.rvlab1.service;

import com.poluectov.rvlab1.dto.marker.MarkerRequestTo;
import com.poluectov.rvlab1.dto.marker.MarkerResponseTo;
import com.poluectov.rvlab1.model.Marker;
import com.poluectov.rvlab1.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MarkerService extends CommonRestService<Marker, MarkerRequestTo, MarkerResponseTo> {
    public MarkerService(@Qualifier("markerRepository") MarkerRepository repository) {
        super(repository);
    }

    @Override
    MarkerResponseTo mapResponseTo(Marker marker) {
        return MarkerResponseTo.builder()
                .id(marker.getId())
                .name(marker.getName())
                .build();
    }

}
