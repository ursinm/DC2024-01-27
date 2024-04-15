package by.bsuir.dc.discussion.configuration;

import by.bsuir.dc.discussion.service.mapper.MessageMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscussionConfiguration {

    @Bean
    public MessageMapper messageMapper() {
        return Mappers.getMapper(MessageMapper.class);
    }

}
