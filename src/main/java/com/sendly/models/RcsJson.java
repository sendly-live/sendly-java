package com.sendly.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

final class RcsJson {
    private RcsJson() {}

    static String string(JsonObject json, String key) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsString() : null;
    }

    static Boolean bool(JsonObject json, String key) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonPrimitive() ? e.getAsBoolean() : null;
    }

    static JsonObject object(JsonObject json, String key) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonObject() ? e.getAsJsonObject() : null;
    }

    static JsonArray array(JsonObject json, String key) {
        JsonElement e = json.get(key);
        return e != null && e.isJsonArray() ? e.getAsJsonArray() : null;
    }

    static List<String> strings(JsonObject json, String key) {
        JsonArray arr = array(json, key);
        if (arr == null) {
            return null;
        }
        List<String> out = new ArrayList<>();
        for (JsonElement e : arr) {
            if (e.isJsonPrimitive()) {
                out.add(e.getAsString());
            }
        }
        return out;
    }

    static List<JsonObject> objects(JsonObject json, String key) {
        JsonArray arr = array(json, key);
        if (arr == null) {
            return null;
        }
        List<JsonObject> out = new ArrayList<>();
        for (JsonElement e : arr) {
            if (e.isJsonObject()) {
                out.add(e.getAsJsonObject());
            }
        }
        return out;
    }

    static void put(JsonObject json, String key, String value) {
        if (value != null) {
            json.addProperty(key, value);
        }
    }

    static void put(JsonObject json, String key, Boolean value) {
        if (value != null) {
            json.addProperty(key, value);
        }
    }

    static void put(JsonObject json, String key, JsonElement value) {
        if (value != null) {
            json.add(key, value);
        }
    }

    static JsonArray stringArray(List<String> values) {
        if (values == null) {
            return null;
        }
        JsonArray arr = new JsonArray();
        for (String v : values) {
            if (v != null) {
                arr.add(v);
            }
        }
        return arr;
    }
}
