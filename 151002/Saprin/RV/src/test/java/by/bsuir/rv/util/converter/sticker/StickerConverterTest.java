package by.bsuir.rv.util.converter.sticker;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class StickerConverterTest {

    @Test
    void convertToResponse_shouldConvertStickerToResponse() {
        Sticker sticker = new Sticker(BigInteger.ONE, "Test Sticker", BigInteger.ONE);
        StickerConverter stickerConverter = new StickerConverter();

        StickerResponseTo response = stickerConverter.convertToResponse(sticker);

        assertNotNull(response);
        assertEquals(sticker.getId(), response.getId());
        assertEquals(sticker.getName(), response.getName());
    }

    @Test
    void convertToEntity_shouldConvertStickerRequestToSticker() {
        StickerRequestTo stickerRequest = new StickerRequestTo(BigInteger.ONE, "Test Sticker", BigInteger.ONE);
        StickerConverter stickerConverter = new StickerConverter();

        Sticker sticker = stickerConverter.convertToEntity(stickerRequest);

        assertNotNull(sticker);
        assertEquals(stickerRequest.getId(), sticker.getId());
        assertEquals(stickerRequest.getName(), sticker.getName());
    }
}
