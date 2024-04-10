package services.tweetservice.domain.request;

import jakarta.validation.constraints.Size;
import services.tweetservice.domain.entity.ValidationMarker;

public record MessageRequestTo(
        Long id,
        Long tweetId,
        @Size(min = 3, max = 32, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
        String content) {
}
