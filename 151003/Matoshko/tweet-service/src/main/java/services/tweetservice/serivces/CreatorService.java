package services.tweetservice.serivces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.request.CreatorRequestTo;
import services.tweetservice.domain.response.CreatorResponseTo;

import java.util.List;

public interface CreatorService {
    @Validated(ValidationMarker.OnCreate.class)
    CreatorResponseTo create(@Valid CreatorRequestTo entity);

    List<CreatorResponseTo> read();

    @Validated(ValidationMarker.OnUpdate.class)
    CreatorResponseTo update(@Valid CreatorRequestTo entity);

    void delete(Long id);

    CreatorResponseTo findCreatorById(Long id);
}
