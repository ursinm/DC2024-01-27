package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.Creator;
import by.bsuir.publisher.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final CreatorRepository creatorRepository;

    @Named("creatorIdToCreatorRef")
    public Creator creatorIdToCreatorRef(Long creatorId) {
        return creatorRepository.getReferenceById(creatorId);
    }
}
