package by.bsuir.dc.features.marker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    boolean existsByName(String name);
}