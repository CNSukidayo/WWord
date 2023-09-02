package io.github.cnsukidayo.wword.common.request.type.deser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author sukidayo
 * @date 2023/9/2 16:27
 */
public class GLocalDateDeSerializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String localDate = json.getAsJsonPrimitive().getAsString();
        return LocalDate.parse(localDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
