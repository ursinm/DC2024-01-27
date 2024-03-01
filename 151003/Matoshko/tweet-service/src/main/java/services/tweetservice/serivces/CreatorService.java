package services.tweetservice.serivces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.Creator;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.request.CreatorRequestTo;
import services.tweetservice.domain.response.CreatorResponseTo;

import java.util.List;
import java.util.Optional;

public interface CreatorService {
    @Validated(ValidationMarker.OnCreate.class)
    CreatorResponseTo create(@Valid CreatorRequestTo entity);

    List<CreatorResponseTo> read();

    @Validated(ValidationMarker.OnUpdate.class)
    CreatorResponseTo update(@Valid CreatorRequestTo entity);

    void delete(Long id);

    CreatorResponseTo findCreatorById(Long id);

    boolean existsByIdExt(Long id);

    Optional<Creator> findCreatorByIdExt(Long id);

    Optional<Creator> getReferenceByIdExt(Long id);
}
