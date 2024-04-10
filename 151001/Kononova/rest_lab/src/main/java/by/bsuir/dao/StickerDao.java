package by.bsuir.dao;

import by.bsuir.entities.Sticker;

import java.util.Optional;

public interface StickerDao extends CrudDao<Sticker> {

    Optional<Sticker> getStickerByIssueId (long issueId);
}
