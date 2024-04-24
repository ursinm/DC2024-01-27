package com.poluectov.rvproject.service.kafka;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class KafkaSendReceiverMap<ID> {

    private final ConcurrentMap<ID, KafkaSendReceiver<?>> sendReceiverConcurrentMap;
    Executor executorService;

    public KafkaSendReceiverMap() {
        this.sendReceiverConcurrentMap = new ConcurrentHashMap<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public Thread add(ID id, Long timeout) {
        KafkaSendReceiver<?> responseWait = new KafkaSendReceiver<>(timeout);
        sendReceiverConcurrentMap.put(id, responseWait);
        Runnable task = responseWait::receive;
        return new Thread(task);
    }

    public KafkaSendReceiver<?> get(ID id) {
        return sendReceiverConcurrentMap.get(id);
    }
    public Boolean containsKey(ID id) {
        return sendReceiverConcurrentMap.containsKey(id);
    }
    public KafkaSendReceiver<?> remove(ID id) {
        return sendReceiverConcurrentMap.remove(id);
    }

}
