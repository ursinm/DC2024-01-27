package com.poluectov.rvlab1.repository;

import com.poluectov.rvlab1.dto.marker.MarkerRequestTo;
import com.poluectov.rvlab1.model.Marker;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
@Component
public interface MarkerRepository extends ICommonRepository<Marker, MarkerRequestTo> {

    List<Marker> findAll(List<BigInteger> markerIds);
}
