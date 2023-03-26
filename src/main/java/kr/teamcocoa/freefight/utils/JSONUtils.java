package kr.teamcocoa.freefight.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JSONUtils {

    private static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping().create();

    public static JsonElement parse(String string) {
        return JsonParser.parseString(string);
    }

    public static String stringify(JsonElement jsonElement) {
        return gson.toJson(jsonElement);
    }


}
