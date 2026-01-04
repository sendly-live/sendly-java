package com.sendly.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an API key.
 */
public class ApiKey {
    private final String id;
    private final String name;
    private final String type;
    private final String prefix;
    private final String lastFour;
    private final List<String> permissions;
    private final String createdAt;
    private final String lastUsedAt;
    private final String expiresAt;
    private final boolean isRevoked;

    public ApiKey(JsonObject json) {
        this.id = getStringOrNull(json, "id");
        this.name = getStringOrNull(json, "name");
        this.type = getStringOrNull(json, "type");
        this.prefix = getStringOrNull(json, "prefix");
        this.lastFour = getStringOrNull(json, "last_four", "lastFour");
        this.permissions = getStringList(json, "permissions");
        this.createdAt = getStringOrNull(json, "created_at", "createdAt");
        this.lastUsedAt = getStringOrNull(json, "last_used_at", "lastUsedAt");
        this.expiresAt = getStringOrNull(json, "expires_at", "expiresAt");
        this.isRevoked = getBoolOrDefault(json, "is_revoked", "isRevoked", false);
    }

    private String getStringOrNull(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
    }

    private String getStringOrNull(JsonObject json, String key1, String key2) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsString();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsString();
        return null;
    }

    private boolean getBoolOrDefault(JsonObject json, String key1, String key2, boolean defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsBoolean();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsBoolean();
        return defaultVal;
    }

    private List<String> getStringList(JsonObject json, String key) {
        List<String> list = new ArrayList<>();
        if (json.has(key) && json.get(key).isJsonArray()) {
            JsonArray arr = json.getAsJsonArray(key);
            for (int i = 0; i < arr.size(); i++) {
                if (!arr.get(i).isJsonNull()) {
                    list.add(arr.get(i).getAsString());
                }
            }
        }
        return list;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getPrefix() { return prefix; }
    public String getLastFour() { return lastFour; }
    public List<String> getPermissions() { return permissions; }
    public String getCreatedAt() { return createdAt; }
    public String getLastUsedAt() { return lastUsedAt; }
    public String getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return isRevoked; }

    @Override
    public String toString() {
        return "ApiKey{id='" + id + "', name='" + name + "', type='" + type + "'}";
    }
}
