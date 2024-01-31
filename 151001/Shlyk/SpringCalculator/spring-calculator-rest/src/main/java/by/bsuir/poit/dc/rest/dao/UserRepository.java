package by.bsuir.poit.dc.rest.dao;

import by.bsuir.poit.dc.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}