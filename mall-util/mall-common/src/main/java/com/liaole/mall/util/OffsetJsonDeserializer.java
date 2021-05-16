package com.liaole.mall.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class OffsetJsonDeserializer extends JsonDeserializer<LocalDateTime> {


    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            OffsetDateTime offsetDateTime = InstantDeserializer.OFFSET_DATE_TIME.deserialize(jsonParser,deserializationContext);
            if(!Objects.isNull(offsetDateTime)){
                ZoneOffset localOffset = OffsetDateTime.now().getOffset();
                return offsetDateTime.withOffsetSameInstant(localOffset).toLocalDateTime();
            }
        }catch (MismatchedInputException e){
            return LocalDateTimeDeserializer.INSTANCE.deserialize(jsonParser,deserializationContext);
        }
        return null;
    }
}
