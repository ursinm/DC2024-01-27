package com.example.rv.impl.tag;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StickerMapperImpl implements StickerMapper {
    @Override
    public StickerRequestTo tagToRequestTo(Sticker sticker) {
        return new StickerRequestTo(
                sticker.getId(),
                sticker.getName()
        );
    }

    @Override
    public List<StickerRequestTo> tagToRequestTo(Iterable<Sticker> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToRequestTo)
                .collect(Collectors.toList());
    }

    @Override
    public Sticker dtoToEntity(StickerRequestTo stickerRequestTo) {
        return new Sticker(
                stickerRequestTo.id(),
                stickerRequestTo.name()
        );
    }

    @Override
    public List<Sticker> dtoToEntity(Iterable<StickerRequestTo> tagRequestTos) {
        return StreamSupport.stream(tagRequestTos.spliterator(), false)
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StickerResponseTo tagToResponseTo(Sticker sticker) {
        return new StickerResponseTo(
                sticker.getId(),
                sticker.getName()
        );
    }

    @Override
    public List<StickerResponseTo> tagToResponseTo(Iterable<Sticker> tags) {
        return StreamSupport.stream(tags.spliterator(), false)
                .map(this::tagToResponseTo)
                .collect(Collectors.toList());
    }
}
