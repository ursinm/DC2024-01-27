package org.example.publisher.impl.story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface StoryRepository extends JpaRepository<Story, BigInteger> {
}