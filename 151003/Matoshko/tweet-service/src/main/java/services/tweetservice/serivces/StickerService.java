package services.tweetservice.serivces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.request.StickerRequestTo;
import services.tweetservice.domain.response.StickerResponseTo;

import java.util.List;

public interface StickerService {
    @Validated(ValidationMarker.OnCreate.class)
    StickerResponseTo create(@Valid StickerRequestTo entity);

    List<StickerResponseTo> read();

    @Validated(ValidationMarker.OnUpdate.class)
    StickerResponseTo update(@Valid StickerRequestTo entity);

    void delete(Long id);

    StickerResponseTo findStickerById(Long Id);
}
