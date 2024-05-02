package com.luschickij.publisher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luschickij.publisher.controller.redis.RedisMessageSubscriber;
import com.luschickij.publisher.dto.KafkaPostResponseTo;
import com.luschickij.publisher.service.kafka.KafkaSendReceiverMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String hostName;


    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.timeout}")
    private int timeout;

    @Value("${redis.pubsub.channel}")
    private String channel;

    private final KafkaSendReceiverMap<UUID> sendReceiverMap;

    private final ObjectMapper objectMapper;

    @Bean
    public LettuceConnectionFactory connectionFactory() {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);


        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout))
                .build();

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, lettuceClientConfiguration);


        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate() {

        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<KafkaPostResponseTo>(KafkaPostResponseTo.class));


        redisTemplate.afterPropertiesSet();


        return redisTemplate;
    }

    @Bean
    public ChannelTopic kafkaResponseTopic() {
        return new ChannelTopic(channel);
    }

    @Bean
    public RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer redisPostListenerContainer = new RedisMessageListenerContainer();
        redisPostListenerContainer.setConnectionFactory(connectionFactory());
        redisPostListenerContainer.addMessageListener(listenerAdapter, kafkaResponseTopic());
        return redisPostListenerContainer;
    }

    @Bean
    public MessageListenerAdapter postListenerAdapter() {
        return new MessageListenerAdapter(new RedisMessageSubscriber(sendReceiverMap, objectMapper), "onPost");
    }
}
