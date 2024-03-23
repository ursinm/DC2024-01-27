package by.bsuir.rv.util.converter.sticker;

import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StickerConverterTest {

    @Test
    void convertToResponse_shouldConvertStickerToResponse() {
        Sticker sticker = new Sticker(BigInteger.ONE, "Test Sticker", new ArrayList<>());
        StickerConverter stickerConverter = new StickerConverter();

        StickerResponseTo response = stickerConverter.convertToResponse(sticker);

        assertNotNull(response);
        assertEquals(sticker.getSt_id(), response.getId());
        assertEquals(sticker.getSt_name(), response.getName());
    }

    @Test
    void convertToEntity_shouldConvertStickerRequestToSticker() {
        StickerRequestTo stickerRequest = new StickerRequestTo(BigInteger.ONE, "Test Sticker", new ArrayList<>());
        StickerConverter stickerConverter = new StickerConverter();

        Sticker sticker = stickerConverter.convertToEntity(stickerRequest, new ArrayList<>());

        assertNotNull(sticker);
        assertEquals(stickerRequest.getId(), sticker.getSt_id());
        assertEquals(stickerRequest.getName(), sticker.getSt_name());
    }
}
