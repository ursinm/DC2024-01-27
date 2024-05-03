package org.example.publisher.impl.story.dto;

import java.math.BigInteger;
import java.util.Date;

public record StoryResponseTo (
        BigInteger id,
        BigInteger userId,
        String title,
        String content,
        Date created,
        Date modified
){

}
