package com.example.rw.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configMap = new HashMap<>();
        configMap.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configMap);
    }

    @Bean
    public NewTopic messageInTopic(){
        return new NewTopic("inTopic",1,(short)1);
    }

    @Bean
    public NewTopic messageOutTopic(){
        return new NewTopic("outTopic",1,(short)1);
    }
}
