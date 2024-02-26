package extension.helpers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * https://jcp.org/en/jsr/detail?id=353
 */
public class JsonBuilder {

    private JsonBuilder() {
    }

    public static JsonObjectBuilder createObjectBuilder() {
        return new JsonObjectBuilder();
    }

    public static JsonArrayBuilder createArrayBuilder() {
        return new JsonArrayBuilder();
    }

    public static <T> JsonArray toJsonArray(Collection<T> list, Function<T, JsonElement> toJson) {
        return list.stream()
            .map(toJson)
            .collect(toJsonArray());
    }

    public static Collector<JsonElement, JsonArrayBuilder, JsonArray> toJsonArray() {
        return Collector.of(
            JsonBuilder::createArrayBuilder,
            JsonArrayBuilder::add,
            JsonArrayBuilder::addAll,
            JsonArrayBuilder::build);
    }
}
