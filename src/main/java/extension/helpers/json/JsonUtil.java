package extension.helpers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import extension.helpers.FileUtil;
import extension.helpers.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author isayan
 */
public class JsonUtil {

    public static boolean validJson(String jsonElementString) {
        try {
            JsonParser.parseString(jsonElementString);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    public static String stringifyJson(JsonElement jsonElement) {
        return prettyJson(jsonElement, false);
    }

    public static JsonElement parseJson(String jsonElementString) throws JsonSyntaxException {
        return JsonParser.parseString(jsonElementString);
    }

    public static JsonObject parseJsonObject(String jsonElementString) throws JsonSyntaxException {
        JsonElement json = JsonParser.parseString(jsonElementString);
        return json.getAsJsonObject();
    }

    public static String prettyJson(String jsonString) throws IOException {
        return prettyJson(jsonString, true);
    }

    public static String prettyJson(String jsonElementString, boolean pretty) throws JsonSyntaxException {
        return prettyJson(parseJson(jsonElementString), pretty);
    }

    public static String prettyJson(JsonElement jsonElement, boolean pretty) {
        if (pretty) {
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
            return gson.toJson(jsonElement);
        } else {
            Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
            return gson.toJson(jsonElement);
        }
    }

    public static DefaultTreeModel toTreeNodeModel(String rootName) {
        DefaultMutableTreeNode rootJson = new DefaultMutableTreeNode(rootName);
        DefaultTreeModel model = new DefaultTreeModel(rootJson);
        return model;
    }

    public static DefaultTreeModel toJsonTreeModel(JsonElement jsonElement) {
        return toJsonTreeModel(jsonElement, "JSON");
    }

    public static DefaultTreeModel toJsonTreeModel(JsonElement jsonElement, String rootName) {
        DefaultMutableTreeNode rootJson = new DefaultMutableTreeNode(rootName);
        DefaultTreeModel model = new DefaultTreeModel(rootJson);
        toJsonTreeNode(jsonElement, rootJson);
        return model;
    }

    public static DefaultTreeModel toJsonTreeModel(JsonpElement jsonpElement) {
        DefaultMutableTreeNode rootJson = new DefaultMutableTreeNode("JSONP");
        DefaultTreeModel model = new DefaultTreeModel(rootJson);
        rootJson.add(new DefaultMutableTreeNode(jsonpElement.getCallbackName() + "()"));
        toJsonTreeNode(jsonpElement.getJsonElement(), rootJson);
        return model;
    }

    private static void toJsonTreeNode(JsonElement jsonElement, DefaultMutableTreeNode parentNode) {
        if (jsonElement.isJsonObject()) {
//            DefaultMutableTreeNode node = new DefaultMutableTreeNode("{}");
//            parentNode.add(node);
            DefaultMutableTreeNode node = parentNode;
            JsonObject jsonObject = (JsonObject) jsonElement;
            Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> s : set) {
                JsonElement value = s.getValue();
                if (value.isJsonNull()) {
                    DefaultMutableTreeNode jsonKeySet = new DefaultMutableTreeNode(s);
                    node.add(jsonKeySet);
                } else if (value.isJsonPrimitive()) {
                    DefaultMutableTreeNode jsonKeySet = new DefaultMutableTreeNode(s);
                    node.add(jsonKeySet);
                } else {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(s.getKey());
                    node.add(childNode);
                    toJsonTreeNode(value, childNode);
                }
            }
        } else if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = (JsonArray) jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement value = jsonArray.get(i);
                toJsonTreeNode(value, parentNode);
            }
        } else if (jsonElement.isJsonNull()) {
            DefaultMutableTreeNode jsonKeySet = new DefaultMutableTreeNode(jsonElement);
            parentNode.add(jsonKeySet);
        } else if (jsonElement.isJsonPrimitive()) {
            DefaultMutableTreeNode jsonKeySet = new DefaultMutableTreeNode(jsonElement);
            parentNode.add(jsonKeySet);
        }
    }

    private final static Pattern JSON_TYPE = Pattern.compile("((\\[(.*)\\])|(\\{(.*)\\}))", Pattern.DOTALL);

    public static boolean isJson(String jsonString) {
        jsonString = jsonString.trim();
        Matcher m = JSON_TYPE.matcher(jsonString);
        if (m.lookingAt()) {
            return JsonUtil.validJson(jsonString);
        } else {
            return false;
        }
    }

    public static boolean isJsonp(String jsonpString) {
        try {
            JsonpElement.parseJsonp(jsonpString);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    private static final Map<Class<?>, Object> typeAdapterMap = new HashMap<>();

    public static void registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
        typeAdapterMap.put(baseType, typeAdapter);
    }

    public static void removeTypeHierarchyAdapter(Class<?> baseType) {
        typeAdapterMap.remove(baseType);
    }

    public static void saveToJson(File fo, Object bean, boolean exludeFields) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(bean);
        FileUtil.bytesToFile(StringUtil.getBytesUTF8(jsonString), fo);
    }

    public static <T> T loadFromJson(File fi, Class<T> classOfT, boolean exludeFields) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        String jsonString = FileUtil.stringFromFile(fi, StandardCharsets.UTF_8);
        return gson.fromJson(jsonString, classOfT);
    }

    public static JsonElement jsonToJsonElement(Object jsonObject, boolean exludeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJsonTree(jsonObject);
    }

    public static <T> T jsonFromJsonElement(JsonElement jsonElement, Class<T> classOfT, boolean exludeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gson.toJson(jsonElement), classOfT);
    }

    public static <T> T jsonFromJsonElement(JsonElement jsonElement, Type listType, boolean exludeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gson.toJson(jsonElement), listType);
    }

    public static String jsonToString(Object bean, boolean exludeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJson(bean);
    }

    public static <T> T jsonFromString(String jsonString, Class<T> classOfT, boolean exludeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, classOfT);
    }

    public static <T> T jsonFromString(String jsonString, Type type, boolean exludeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        for (Map.Entry<Class<?>, Object> set : typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeHierarchyAdapter(set.getKey(), set.getValue());
        }
        if (exludeFields) {
            gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, type);
    }

    public static void loadFromJson(File fi, Map<String, String> option) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        String jsonString = FileUtil.stringFromFile(fi, StandardCharsets.UTF_8);
        JsonElement jsonRoot = JsonUtil.parseJson(jsonString);
        if (jsonRoot.isJsonObject()) {
            JsonObject jsonMap = jsonRoot.getAsJsonObject();
            for (String memberName : jsonMap.keySet()) {
                option.put(memberName, jsonMap.get(memberName).toString());
            }
        }
    }

    public static void saveToJson(File fo, Map<String, String> option) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        JsonObject jsonMap = new JsonObject();
        for (String memberName : option.keySet()) {
            jsonMap.add(memberName, JsonUtil.parseJson(option.get(memberName)));
        }
        String jsonString = gson.toJson(jsonMap);
        FileUtil.bytesToFile(StringUtil.getBytesUTF8(jsonString), fo);
    }

}
