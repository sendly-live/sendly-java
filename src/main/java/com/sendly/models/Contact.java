package com.sendly.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.HashMap;

public class Contact {
    private String id;
    @SerializedName("phone_number")
    private String phoneNumber;
    private String name;
    private String email;
    private Map<String, Object> metadata;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public Contact() {}

    public Contact(JsonObject json) {
        if (json.has("id")) this.id = json.get("id").getAsString();
        if (json.has("phone_number")) this.phoneNumber = json.get("phone_number").getAsString();
        if (json.has("name") && !json.get("name").isJsonNull()) {
            this.name = json.get("name").getAsString();
        }
        if (json.has("email") && !json.get("email").isJsonNull()) {
            this.email = json.get("email").getAsString();
        }
        if (json.has("metadata") && json.get("metadata").isJsonObject()) {
            this.metadata = new HashMap<>();
            json.get("metadata").getAsJsonObject().entrySet().forEach(e -> {
                if (e.getValue().isJsonPrimitive()) {
                    if (e.getValue().getAsJsonPrimitive().isString()) {
                        metadata.put(e.getKey(), e.getValue().getAsString());
                    } else if (e.getValue().getAsJsonPrimitive().isNumber()) {
                        metadata.put(e.getKey(), e.getValue().getAsNumber());
                    } else if (e.getValue().getAsJsonPrimitive().isBoolean()) {
                        metadata.put(e.getKey(), e.getValue().getAsBoolean());
                    }
                }
            });
        }
        if (json.has("created_at")) this.createdAt = json.get("created_at").getAsString();
        if (json.has("updated_at")) this.updatedAt = json.get("updated_at").getAsString();
    }

    public String getId() { return id; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Map<String, Object> getMetadata() { return metadata; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
