package by.bsuir.news.discussion.config;

import by.bsuir.news.discussion.entity.converter.LocaleReadConverter;
import by.bsuir.news.discussion.entity.converter.LocaleWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;

@Configuration
public class CassandraConfig {
    @Bean
    public CassandraCustomConversions cassandraCustomConversions(LocaleWriteConverter localWriteConverter,
                                                                 LocaleReadConverter localeReadConverter) {
        return CassandraCustomConversions.create(config -> {
            config.registerConverter(localeReadConverter);
            config.registerConverter(localWriteConverter);
        });
    }
}