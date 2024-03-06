package by.bsuir.rv.repository.comment;

import by.bsuir.rv.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CommentRepository extends JpaRepository<Comment, BigInteger> {
}
