package by.bsuir.publicator.util.converter.sticker;

import by.bsuir.publicator.bean.Issue;
import by.bsuir.publicator.bean.Sticker;
import by.bsuir.publicator.dto.StickerRequestTo;
import by.bsuir.publicator.dto.StickerResponseTo;
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
