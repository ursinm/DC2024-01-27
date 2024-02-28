package by.bsuir.dao;

import by.bsuir.entities.Comment;
import by.bsuir.entities.Label;

import java.util.Optional;

public interface CommentDao extends CrudDao<Comment> {
    Optional<Comment> getCommentByIssueId (long issueId);
}
