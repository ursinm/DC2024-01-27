package com.luschickij.discussion.service.kafka;

public enum KafkaPostType {
    GET_ALL("GET_ALL"),
    SAVE("SAVE"),
    DELETE("DELETE"),
    ONE("ONE"),
    UPDATE("UPDATE");

    private final String value;

    KafkaPostType(String value) {
        this.value = value;
    }

    public KafkaPostType fromValue(String value) {
        for (KafkaPostType type : KafkaPostType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
