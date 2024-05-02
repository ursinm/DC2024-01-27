package com.luschickij.publisher.service.kafka;


import java.util.Date;
import java.util.concurrent.TimeoutException;

public class KafkaSendReceiver<T> {

    private T post;
    private final Long timeout;
    private final Date start;
    private TimeoutException timeoutException;

    private volatile boolean transfer = true;

    public KafkaSendReceiver(Long timeout) {
        this.start = new Date();
        this.timeout = timeout;
    }

    public synchronized void receive() {
        while (transfer) {
            if (timeout != 0 && start.before(new Date(System.currentTimeMillis() - timeout))) {
                timeoutException = new TimeoutException();
                Thread.currentThread().interrupt();
                return;
            }
            try {
                wait(timeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Thread.currentThread().interrupt();
    }

    public synchronized void send(T data) {
        transfer = false;
        this.post = data;
        notifyAll();
    }

    public T getPost() throws TimeoutException {
        if (timeoutException != null) {
            throw timeoutException;
        }
        return post;
    }

}
