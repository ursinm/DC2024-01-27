package by.bsuir.dao;

import by.bsuir.entities.Comment;
import by.bsuir.entities.Issue;

import java.util.List;

public interface CommentDao {
    Comment createComment(String content, Issue issue);

    List<Comment> getComments();

    Comment getCommentById(Long Id);

    Comment updateComment(Long id, Comment newComment);

    void deleteComment(Long id);

    List<Comment> getCommentsByIssueId(Long issueId);
}
