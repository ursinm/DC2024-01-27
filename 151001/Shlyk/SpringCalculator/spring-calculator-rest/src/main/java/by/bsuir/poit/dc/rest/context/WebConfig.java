package by.bsuir.poit.dc.rest.context;

import by.bsuir.poit.dc.rest.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.rest.api.dto.response.LabelDto;
import by.bsuir.poit.dc.rest.api.dto.response.NewsDto;
import by.bsuir.poit.dc.rest.api.dto.response.NoteDto;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * @author Paval Shlyk
 * @since 05/03/2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public RestTemplate cassandraTemplate() {
	return new RestTemplateBuilder().
		   rootUri("localhost:24130/api/v1.0")
		   .build();
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
	return builder -> builder
			      .withCacheConfiguration("noteCache", forClass(NoteDto.class, 12))
			      .withCacheConfiguration("newsCache", forClass(NewsDto.class, 30))
			      .withCacheConfiguration("labelCache", forClass(LabelDto.class, 50));
    }

    private <T> RedisCacheConfiguration forClass(Class<T> clazz, int timeout) {
	return RedisCacheConfiguration.defaultCacheConfig()
		   .entryTtl(Duration.ofSeconds(timeout))
		   .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(ProtobufRedisSerializer.with(clazz)))
		   .disableCachingNullValues();

    }
}
