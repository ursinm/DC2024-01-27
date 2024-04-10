package com.example.restapplication.utils;

import com.example.restapplication.exceptions.NumberInsteadOfStringException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StringParser extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken != JsonToken.VALUE_STRING) {
            throw new NumberInsteadOfStringException("Field value is not a string in JSON", 40006L);
        } else {
            return jsonParser.getText();
        }
    }
}
