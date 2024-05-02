package com.luschickij.publisher.controller.redis;

public interface PostPublisher<M> {

    void publish(M post);
}
