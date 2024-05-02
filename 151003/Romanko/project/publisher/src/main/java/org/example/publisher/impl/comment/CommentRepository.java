package org.example.publisher.impl.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface CommentRepository extends JpaRepository<Comment, BigInteger> {
}