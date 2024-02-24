package com.distributed_computing.rest.repository;

import com.distributed_computing.rest.bean.Marker;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MarkerRepository {

    Map<Integer, Marker> markers = new HashMap<>();

    public List<Marker> getAll(){
        List<Marker> res = new ArrayList<>();

        for(Map.Entry<Integer, Marker> entry : markers.entrySet()){
            res.add(entry.getValue());
        }

        return res;
    }

    public Optional<Marker> save(Marker marker){
        Marker prevMarker = markers.getOrDefault(marker.getId(), null);
        markers.put(marker.getId(), marker);
        return Optional.ofNullable(prevMarker);
    }


    public Optional<Marker> getById(int id){
        return Optional.ofNullable(markers.getOrDefault(id, null));
    }

    public Marker delete(int id){
        return markers.remove(id);
    }
}
