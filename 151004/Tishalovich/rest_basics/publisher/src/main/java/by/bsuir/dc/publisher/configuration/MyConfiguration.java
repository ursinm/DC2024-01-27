package by.bsuir.dc.publisher.configuration;

import by.bsuir.dc.publisher.services.impl.mappers.AuthorMapper;
import by.bsuir.dc.publisher.services.impl.mappers.MessageMapper;
import by.bsuir.dc.publisher.services.validation.*;
import by.bsuir.dc.publisher.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.publisher.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.publisher.services.impl.mappers.LabelMapper;
import by.bsuir.dc.publisher.services.impl.mappers.StoryMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy
public class MyConfiguration {

    @Bean
    public StoryMapper storyMapper() {
        return Mappers.getMapper(StoryMapper.class);
    }

    @Bean
    public LabelMapper labelMapper() {
        return Mappers.getMapper(LabelMapper.class);
    }

    @Bean
    public AuthorMapper authorMapper() {
        return Mappers.getMapper(AuthorMapper.class);
    }

    @Bean
    public MessageMapper messageMapper() {
        return Mappers.getMapper(MessageMapper.class);
    }

    @Bean
    public Map<Class<?>, EntityValidator> validators() {
        Map<Class<?>, EntityValidator> validators = new HashMap<>();

        validators.put(AuthorRequestTo.class, new AuthorValidator());
        validators.put(LabelRequestTo.class, new LabelValidator());
        validators.put(MessageRequestTo.class, new MessageValidator());
        validators.put(StoryRequestTo.class, new StoryValidator());

        return validators;
    }

    @Bean
    public String discussionUri() {
        return "http://localhost:24130/api/v1.0/messages";
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

}
