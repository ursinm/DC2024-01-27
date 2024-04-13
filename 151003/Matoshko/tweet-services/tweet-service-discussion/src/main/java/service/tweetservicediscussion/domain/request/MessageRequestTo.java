package service.tweetservicediscussion.domain.request;

import jakarta.validation.constraints.Size;
import service.tweetservicediscussion.domain.entity.ValidationMarker;

public record MessageRequestTo(
        Long id,
        Long tweetId,
        @Size(min = 3, max = 32, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
        String content) {
}
