package com.sendly.models;

import com.google.gson.JsonObject;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a configured webhook endpoint.
 */
public class Webhook {
    // Circuit state constants
    public static final String CIRCUIT_CLOSED = "closed";
    public static final String CIRCUIT_OPEN = "open";
    public static final String CIRCUIT_HALF_OPEN = "half_open";

    // Mode constants
    public static final String MODE_ALL = "all";
    public static final String MODE_TEST = "test";
    public static final String MODE_LIVE = "live";

    private final String id;
    private final String url;
    private final List<String> events;
    private final String description;
    private final String mode;
    private final boolean isActive;
    private final int failureCount;
    private final Instant lastFailureAt;
    private final String circuitState;
    private final Instant circuitOpenedAt;
    private final String apiVersion;
    private final Map<String, Object> metadata;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final int totalDeliveries;
    private final int successfulDeliveries;
    private final double successRate;
    private final Instant lastDeliveryAt;

    public Webhook(JsonObject json) {
        this.id = getStringOrNull(json, "id");
        this.url = getStringOrNull(json, "url");
        this.events = parseEvents(json);
        this.description = getStringOrNull(json, "description");
        this.mode = getStringOrDefault(json, "mode", "mode", MODE_ALL);
        this.isActive = getBooleanOrDefault(json, "is_active", "isActive", false);
        this.failureCount = getIntOrDefault(json, "failure_count", "failureCount", 0);
        this.lastFailureAt = parseInstant(getStringOrNull(json, "last_failure_at", "lastFailureAt"));
        this.circuitState = getStringOrDefault(json, "circuit_state", "circuitState", CIRCUIT_CLOSED);
        this.circuitOpenedAt = parseInstant(getStringOrNull(json, "circuit_opened_at", "circuitOpenedAt"));
        this.apiVersion = getStringOrDefault(json, "api_version", "apiVersion", "2024-01");
        this.metadata = parseMetadata(json);
        this.createdAt = parseInstant(getStringOrNull(json, "created_at", "createdAt"));
        this.updatedAt = parseInstant(getStringOrNull(json, "updated_at", "updatedAt"));
        this.totalDeliveries = getIntOrDefault(json, "total_deliveries", "totalDeliveries", 0);
        this.successfulDeliveries = getIntOrDefault(json, "successful_deliveries", "successfulDeliveries", 0);
        this.successRate = getDoubleOrDefault(json, "success_rate", "successRate", 0.0);
        this.lastDeliveryAt = parseInstant(getStringOrNull(json, "last_delivery_at", "lastDeliveryAt"));
    }

    private List<String> parseEvents(JsonObject json) {
        List<String> result = new ArrayList<>();
        if (json.has("events") && json.get("events").isJsonArray()) {
            json.getAsJsonArray("events").forEach(e -> result.add(e.getAsString()));
        }
        return result;
    }

    private Map<String, Object> parseMetadata(JsonObject json) {
        Map<String, Object> result = new HashMap<>();
        if (json.has("metadata") && json.get("metadata").isJsonObject()) {
            json.getAsJsonObject("metadata").entrySet().forEach(e ->
                result.put(e.getKey(), e.getValue().isJsonPrimitive() ? e.getValue().getAsString() : e.getValue())
            );
        }
        return result;
    }

    private String getStringOrNull(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
    }

    private String getStringOrNull(JsonObject json, String key1, String key2) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsString();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsString();
        return null;
    }

    private String getStringOrDefault(JsonObject json, String key1, String key2, String defaultVal) {
        String val = getStringOrNull(json, key1, key2);
        return val != null ? val : defaultVal;
    }

    private boolean getBooleanOrDefault(JsonObject json, String key1, String key2, boolean defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsBoolean();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsBoolean();
        return defaultVal;
    }

    private int getIntOrDefault(JsonObject json, String key1, String key2, int defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsInt();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsInt();
        return defaultVal;
    }

    private double getDoubleOrDefault(JsonObject json, String key1, String key2, double defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsDouble();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsDouble();
        return defaultVal;
    }

    private Instant parseInstant(String value) {
        if (value == null) return null;
        try {
            return Instant.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Getters
    public String getId() { return id; }
    public String getUrl() { return url; }
    public List<String> getEvents() { return events; }
    public String getDescription() { return description; }
    public String getMode() { return mode; }
    public boolean isActive() { return isActive; }
    public int getFailureCount() { return failureCount; }
    public Instant getLastFailureAt() { return lastFailureAt; }
    public String getCircuitState() { return circuitState; }
    public Instant getCircuitOpenedAt() { return circuitOpenedAt; }
    public String getApiVersion() { return apiVersion; }
    public Map<String, Object> getMetadata() { return metadata; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public int getTotalDeliveries() { return totalDeliveries; }
    public int getSuccessfulDeliveries() { return successfulDeliveries; }
    public double getSuccessRate() { return successRate; }
    public Instant getLastDeliveryAt() { return lastDeliveryAt; }

    public boolean isCircuitOpen() { return CIRCUIT_OPEN.equals(circuitState); }

    @Override
    public String toString() {
        return "Webhook{id='" + id + "', url='" + url + "', isActive=" + isActive + "}";
    }
}
