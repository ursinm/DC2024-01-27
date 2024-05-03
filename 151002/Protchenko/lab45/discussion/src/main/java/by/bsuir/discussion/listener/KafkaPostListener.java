package by.bsuir.discussion.listener;

import by.bsuir.discussion.event.*;
import by.bsuir.discussion.model.dto.PostResponseDto;
import by.bsuir.discussion.model.entity.Post;
import by.bsuir.discussion.model.entity.PostState;
import by.bsuir.discussion.model.mapper.PostKafkaMapper;
import by.bsuir.discussion.repository.PostRepository;
import by.bsuir.discussion.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaPostListener {

    public static final String IN_TOPIC = "in-topic";
    public static final String OUT_TOPIC = "out-topic";

    private final PostKafkaMapper postKafkaMapper;
    private final PostService postService;
    private final PostRepository postRepository;
    private final KafkaTemplate<String, OutTopicEvent> kafkaTemplate;

    @KafkaListener(topics = IN_TOPIC, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void process(ConsumerRecord<String, InTopicEvent> record) {
        final String key = record.key();
        final InTopicEvent inTopicEvent = record.value();
        final InTopicMessage inTopicMsg = inTopicEvent.message();
        final PostInTopicDto inTopicPost = inTopicMsg.postDto();
        log.info(IN_TOPIC + ": key = " + key + "; operation = " + inTopicMsg.operation() + "; data = " + inTopicMsg.postDto());
        List<PostOutTopicDto> resultList = switch (inTopicMsg.operation()) {
            case GET_ALL -> postKafkaMapper.responseDtoToOutTopicDto(postService.getAll());
            case GET_BY_ID -> getById(inTopicPost.id());
            case DELETE_BY_ID -> delete(inTopicPost.id());
            case CREATE -> create(inTopicPost);
            case UPDATE -> update(inTopicPost);
        };
        log.info(OUT_TOPIC + ": key = " + key + "; data = " + resultList);
        kafkaTemplate.send(
                OUT_TOPIC,
                key,
                new OutTopicEvent(
                        inTopicEvent.id(),
                        new OutTopicMessage(
                                resultList
                        )
                )
        );
    }

    private List<PostOutTopicDto> getById(Long id) {
        PostResponseDto responseDto;
        try {
            responseDto = postService.get(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return Collections.singletonList(postKafkaMapper.responseDtoToOutTopicDto(responseDto));
    }

    private List<PostOutTopicDto> create(PostInTopicDto dto) {
        Post newEntity = postKafkaMapper.toEntity(dto);
        newEntity.setState(PostState.APPROVE);
        try {
            return Collections.singletonList(postKafkaMapper.entityToDto(postRepository.save(newEntity)));
        } catch (Exception ex) {
            newEntity.setState(PostState.DECLINE);
            return Collections.emptyList();
        }
    }

    private List<PostOutTopicDto> update(PostInTopicDto dto) {
        Optional<Post> optionalPost = postRepository.findByKeyId(dto.id());
        if (optionalPost.isEmpty()) {
            return Collections.emptyList();
        }
        final Post entity = optionalPost.get();
        final Post updated = postKafkaMapper.partialUpdate(dto, entity);
        return Collections.singletonList(postKafkaMapper.entityToDto(postRepository.save(updated)));
    }

    private List<PostOutTopicDto> delete(Long id) {
        Optional<Post> optionalPost = postRepository.findByKeyId(id);
        if (optionalPost.isEmpty()) {
            return Collections.emptyList();
        }
        final Post deleted = optionalPost.get();
        postRepository.delete(deleted);
        return Collections.singletonList(postKafkaMapper.entityToDto(deleted));
    }
}
