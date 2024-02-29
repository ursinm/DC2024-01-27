package by.bsuir.dc.rest_basics.configuration;

import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.services.impl.mappers.AuthorMapper;
import by.bsuir.dc.rest_basics.services.impl.mappers.LabelMapper;
import by.bsuir.dc.rest_basics.services.impl.mappers.MessageMapper;
import by.bsuir.dc.rest_basics.services.impl.mappers.StoryMapper;
import by.bsuir.dc.rest_basics.services.validation.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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

}
