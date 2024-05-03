package com.luschickij.publisher.repository;

import com.luschickij.publisher.dto.post.PostRequestTo;
import com.luschickij.publisher.model.Post;

public interface PostRepository extends ICommonRepository<Post, Long> {

    Post update(Post post);
}
