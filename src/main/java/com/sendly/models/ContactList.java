package com.sendly.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ContactList {
    private String id;
    private String name;
    private String description;
    @SerializedName("contact_count")
    private int contactCount;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public ContactList() {}

    public ContactList(JsonObject json) {
        if (json.has("id")) this.id = json.get("id").getAsString();
        if (json.has("name")) this.name = json.get("name").getAsString();
        if (json.has("description") && !json.get("description").isJsonNull()) {
            this.description = json.get("description").getAsString();
        }
        if (json.has("contact_count")) this.contactCount = json.get("contact_count").getAsInt();
        if (json.has("created_at")) this.createdAt = json.get("created_at").getAsString();
        if (json.has("updated_at")) this.updatedAt = json.get("updated_at").getAsString();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getContactCount() { return contactCount; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
