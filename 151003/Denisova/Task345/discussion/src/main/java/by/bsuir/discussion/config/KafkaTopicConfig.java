package by.bsuir.discussion.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /*
      С введением AdminClient в Kafka теперь можно создавать топики программно.
      Для этого необходимо добавить bean-компонент Spring KafkaAdmin, который будет автоматически добавлять топики
      для всех bean-компонентов типа NewTopic:
*/
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    // Для отправки сообщения Publisher.Producer в топик InTopic
    @Bean
    public NewTopic InTopic() {
        return new NewTopic("InTopic", 1, (short) 1);
    }

    // Для получения сообщения Publisher.Consumer из топика OutTopic
    @Bean
    public NewTopic OutTopic() {
        return new NewTopic("OutTopic", 1, (short) 1);
    }
}
