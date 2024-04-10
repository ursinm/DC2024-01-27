package by.bsuir.dao;

import by.bsuir.entities.Message;

import java.util.Optional;

public interface MessageDao extends CrudDao<Message> {

    Optional<Message> getMessageByIssueId (long issueId);
}
