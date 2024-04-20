package by.harlap.jpa.util.dto.tweet.request;

import by.harlap.jpa.dto.request.CreateTweetDto;
import by.harlap.jpa.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@NoArgsConstructor(staticName = "tweet")
@With
public class CreateTweetDtoTestBuilder implements TestBuilder<CreateTweetDto> {

    private Long authorId = 1L;
    private String title = "sdfgh";
    private String content = "vbnkl";

    @Override
    public CreateTweetDto build() {
        CreateTweetDto tweet = new CreateTweetDto();

        tweet.setAuthorId(authorId);
        tweet.setContent(content);
        tweet.setTitle(title);

        return tweet;
    }
}

