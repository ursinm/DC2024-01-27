package com.example.restapplication;

import com.example.restapplication.dto.MessageRequestTo;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class KafkaPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        if (o == null || ((MessageRequestTo) o).getStoryId() == null) {
            return 0;
        }
        Long storyId = ((MessageRequestTo) o).getStoryId();
        return Math.abs(storyId.hashCode()) % cluster.partitionCountForTopic(s);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
