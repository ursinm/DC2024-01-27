package org.example.publisher.impl.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface TagRepository extends JpaRepository<Tag, BigInteger> {

}
