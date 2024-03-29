package by.bsuir.rv.util.converter.sticker;

import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.bean.Sticker;
import by.bsuir.rv.dto.StickerRequestTo;
import by.bsuir.rv.dto.StickerResponseTo;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class StickerConverter {
    public StickerResponseTo convertToResponse(Sticker sticker) {
        List<BigInteger> issueIds = sticker.getIssues() != null ? sticker.getIssues().stream().map(Issue::getIss_id).toList() : new ArrayList<>();
        return new StickerResponseTo(sticker.getSt_id(), sticker.getSt_name(), issueIds);
    }

    public Sticker convertToEntity(StickerRequestTo stickerRequestTo, List<Issue> issues) {
        return new Sticker(stickerRequestTo.getId(), stickerRequestTo.getName(), issues);
    }
}
