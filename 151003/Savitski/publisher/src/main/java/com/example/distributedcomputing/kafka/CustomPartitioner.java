package com.example.distributedcomputing.kafka;

import com.example.distributedcomputing.model.request.NoteRequestTo;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key == null || ((NoteRequestTo) key).getStoryId() == null) {
            return 0;
        }
        Long issueId = ((NoteRequestTo) key).getStoryId();
        return Math.abs(issueId.hashCode()) % cluster.partitionCountForTopic(topic);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}