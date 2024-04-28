package by.bsuir.dc.publisher.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public String inTopicName() {
        return "InTopic";
    }

    @Bean
    public String outTopicName() {
        return "OutTopic";
    }

    @Bean
    public NewTopic inTopic() {
        return TopicBuilder
                .name(inTopicName())
                .build();
    }

    @Bean
    public NewTopic outTopic() {
        return TopicBuilder
                .name(outTopicName())
                .build();
    }

}