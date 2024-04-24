package com.poluectov.rvproject.config;

import com.poluectov.rvproject.kafkacontroller.Listener;
import com.poluectov.rvproject.kafkacontroller.Sender;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.url}")
    String kafkaUrl;

    @Value("${kafka.topic.message.request}")
    String messageRequestTopic;

    @Value("${kafka.topic.message.response}")
    String messageResponseTopic;

    @Bean
    public NewTopic requestTopic() {
        return TopicBuilder.name(messageRequestTopic)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic responseTopic() {
        return TopicBuilder.name(messageResponseTopic)
                .partitions(10)
                .replicas(1)
                .build();
    }
}
