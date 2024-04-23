package by.denisova.jpa.service;

import by.denisova.jpa.model.Marker;

import java.util.List;

public interface MarkerService {

    Marker findById(Long id);

    void deleteById(Long id);

    Marker save(Marker marker);

    Marker update(Marker marker);

    List<Marker> findAll();
}
