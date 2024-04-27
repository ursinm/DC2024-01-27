package services.tweetservice.serivces.impl;

import lombok.Getter;

@Getter
public enum MessageOperationUri {
    CREATE_MESSAGE_URI("/messages"),
    READ_MESSAGES_URI("/messages"),
    UPDATE_MESSAGE_URI("/messages"),
    DELETE_MESSAGE_URI("/messages/"),
    FIND_MESSAGE_BY_ID_URI("/messages/");

    private final String value;

    MessageOperationUri(String value) {
        this.value = value;
    }
}
