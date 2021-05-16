package com.liaole.mall.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;


public class OffsetJsonSerializer extends JsonSerializer<LocalDateTime> {


    @Override
    public void serialize(LocalDateTime localeDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(Objects.isNull(localeDateTime)){
            return;
        }
        OffsetDateTimeSerializer.INSTANCE.serialize(OffsetDateTime.of(localeDateTime, ZoneOffset.ofHours(8)),jsonGenerator,serializerProvider);
    }
}
