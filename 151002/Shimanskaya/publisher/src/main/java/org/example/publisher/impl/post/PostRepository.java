package org.example.publisher.impl.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface PostRepository extends JpaRepository<Post, BigInteger> {
}