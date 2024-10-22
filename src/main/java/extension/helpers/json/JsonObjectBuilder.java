package extension.helpers.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author isayan
 */
public class JsonObjectBuilder {

    private final JsonObject jsonObject = new JsonObject();

    public JsonObject build() {
        return jsonObject;
    }

    public JsonObjectBuilder add(String name, JsonElement json) {
        jsonObject.add(name, json);
        return this;
    }

    public JsonObjectBuilder addAll(String name, JsonObjectBuilder value) {
        jsonObject.add(name, value.build());
        return this;
    }

    public JsonObjectBuilder add(String name, String value) {
        jsonObject.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, Boolean value) {
        jsonObject.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, Number value) {
        jsonObject.addProperty(name, value);
        return this;
    }

    public JsonObjectBuilder add(String name, Character value) {
        jsonObject.addProperty(name, value);
        return this;
    }

}
