package service.tweetservicediscussion.serivces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import service.tweetservicediscussion.domain.entity.ValidationMarker;
import service.tweetservicediscussion.domain.request.MessageRequestTo;
import service.tweetservicediscussion.domain.response.MessageResponseTo;

import java.util.List;

public interface MessageService {
    @Validated(ValidationMarker.OnCreate.class)
    MessageResponseTo create(@Valid MessageRequestTo entity);

    List<MessageResponseTo> read();

    @Validated(ValidationMarker.OnUpdate.class)
    MessageResponseTo update(@Valid MessageRequestTo entity);

    Long delete(Long id);

    MessageResponseTo findMessageById(Long id);
}
