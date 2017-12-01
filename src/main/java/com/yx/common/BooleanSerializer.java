package com.yx.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * User: NMY
 * Date: 16-8-29
 */
public class BooleanSerializer extends JsonSerializer<Boolean> {
    @Override
    public void serialize(Boolean value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (Boolean.FALSE.equals(value)) {
            jsonGenerator.writeNumber(0);
        } else {
            jsonGenerator.writeNumber(1);
        }
    }
}