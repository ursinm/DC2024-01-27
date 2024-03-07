package services.tweetservice.exceptions;

public class NoSuchStickerException extends IllegalArgumentException{
    private final Long stickerId;

    public NoSuchStickerException(Long stickerId) {
        super(String.format("Sticker with id %d is not found in DB", stickerId));
        this.stickerId = stickerId;
    }
}
