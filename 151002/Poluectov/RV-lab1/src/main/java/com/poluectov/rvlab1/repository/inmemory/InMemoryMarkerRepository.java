package com.poluectov.rvlab1.repository.inmemory;

import com.poluectov.rvlab1.dto.marker.MarkerRequestTo;
import com.poluectov.rvlab1.model.Marker;
import com.poluectov.rvlab1.repository.MarkerRepository;
import com.poluectov.rvlab1.utils.dtoconverter.DtoConverter;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class InMemoryMarkerRepository extends InMemoryRepository<Marker, MarkerRequestTo> implements MarkerRepository {
    public InMemoryMarkerRepository(DtoConverter<MarkerRequestTo, Marker> convert) {
        super(convert);
    }

    @Override
    public List<Marker> findAll(List<BigInteger> markerIds) {
        return data.values().stream().filter(marker -> markerIds.contains(marker.getId())).toList();
    }
}
