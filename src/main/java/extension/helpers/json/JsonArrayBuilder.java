package extension.helpers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 *
 * @author isayan
 */
public class JsonArrayBuilder {

    private final JsonArray jsonArray = new JsonArray();

    public JsonArray build() {
        return jsonArray;
    }

    public JsonArrayBuilder add(JsonElement json) {
        jsonArray.add(json);
        return this;
    }

    public JsonArrayBuilder addAll(JsonArray value) {
        jsonArray.addAll(value);
        return this;
    }

    public JsonArrayBuilder addAll(JsonArrayBuilder builder) {
        jsonArray.addAll(builder.build());
        return this;
    }

    public JsonArrayBuilder add(String value) {
        jsonArray.add(value);
        return this;
    }

    public JsonArrayBuilder add(Boolean value) {
        jsonArray.add(value);
        return this;
    }

    public JsonArrayBuilder add(Number value) {
        jsonArray.add(value);
        return this;
    }

    public JsonArrayBuilder add(Character value) {
        jsonArray.add(value);
        return this;
    }

}
