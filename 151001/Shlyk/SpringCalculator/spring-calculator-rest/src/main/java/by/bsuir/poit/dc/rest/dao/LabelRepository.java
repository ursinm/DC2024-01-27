package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findByName(String name);
}