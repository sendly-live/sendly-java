package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Represents a credit transaction.
 */
public class CreditTransaction {
    private final String id;
    private final String type;
    private final int amount;
    private final int balanceAfter;
    private final String description;
    private final String messageId;
    private final String createdAt;

    public CreditTransaction(JsonObject json) {
        this.id = getStringOrNull(json, "id");
        this.type = getStringOrNull(json, "type");
        this.amount = getIntOrDefault(json, "amount", 0);
        this.balanceAfter = getIntOrDefault(json, "balance_after", "balanceAfter", 0);
        this.description = getStringOrNull(json, "description");
        this.messageId = getStringOrNull(json, "message_id", "messageId");
        this.createdAt = getStringOrNull(json, "created_at", "createdAt");
    }

    private String getStringOrNull(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
    }

    private String getStringOrNull(JsonObject json, String key1, String key2) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsString();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsString();
        return null;
    }

    private int getIntOrDefault(JsonObject json, String key, int defaultVal) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsInt() : defaultVal;
    }

    private int getIntOrDefault(JsonObject json, String key1, String key2, int defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsInt();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsInt();
        return defaultVal;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public int getAmount() { return amount; }
    public int getBalanceAfter() { return balanceAfter; }
    public String getDescription() { return description; }
    public String getMessageId() { return messageId; }
    public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "CreditTransaction{id='" + id + "', type='" + type + "', amount=" + amount + "}";
    }
}
