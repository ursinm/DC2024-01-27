package com.luschickij.publisher.config;

import com.luschickij.publisher.service.kafka.KafkaSendReceiverMap;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

import java.util.UUID;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.url}")
    String kafkaUrl;

    @Value("${kafka.topic.post.request}")
    String postRequestTopic;

    @Value("${kafka.topic.post.response}")
    String postResponseTopic;

    @Bean
    public NewTopic requestTopic() {
        return TopicBuilder.name(postRequestTopic)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic responseTopic() {
        return TopicBuilder.name(postResponseTopic)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaSendReceiverMap<UUID> kafkaSendReceiverMap() {
        return new KafkaSendReceiverMap<>();
    }
}
