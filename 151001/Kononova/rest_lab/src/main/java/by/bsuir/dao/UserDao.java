package by.bsuir.dao;

import by.bsuir.entities.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User> {

    Optional<User> getUserByIssueId(long issueId);
}
