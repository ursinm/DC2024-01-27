package by.bsuir.dao.impl;

import by.bsuir.dao.CommentDao;
import by.bsuir.entities.Comment;
import by.bsuir.entities.Issue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Override
    public Comment createComment(String content, Issue issue) {
        return null;
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }

    @Override
    public Comment getCommentById(Long Id) {
        return null;
    }

    @Override
    public Comment updateComment(Long id, Comment newComment) {
        return null;
    }

    @Override
    public void deleteComment(Long id) {

    }

    @Override
    public List<Comment> getCommentsByIssueId(Long issueId) {
        return null;
    }
}
