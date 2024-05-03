package org.education.bean.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseTo {

    @JsonAlias("id")
    int key;
    int tweetId;
    String content;

    @JsonGetter("id")
    public int getKey() {
        return key;
    }
}
