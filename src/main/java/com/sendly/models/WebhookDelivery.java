package com.sendly.models;

import com.google.gson.JsonObject;

import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * Represents a webhook delivery attempt.
 */
public class WebhookDelivery {
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_DELIVERED = "delivered";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_CANCELLED = "cancelled";

    private final String id;
    private final String webhookId;
    private final String eventId;
    private final String eventType;
    private final int attemptNumber;
    private final int maxAttempts;
    private final String status;
    private final Integer responseStatusCode;
    private final Integer responseTimeMs;
    private final String errorMessage;
    private final String errorCode;
    private final Instant nextRetryAt;
    private final Instant createdAt;
    private final Instant deliveredAt;

    public WebhookDelivery(JsonObject json) {
        this.id = getStringOrNull(json, "id");
        this.webhookId = getStringOrNull(json, "webhook_id", "webhookId");
        this.eventId = getStringOrNull(json, "event_id", "eventId");
        this.eventType = getStringOrNull(json, "event_type", "eventType");
        this.attemptNumber = getIntOrDefault(json, "attempt_number", "attemptNumber", 1);
        this.maxAttempts = getIntOrDefault(json, "max_attempts", "maxAttempts", 6);
        this.status = getStringOrNull(json, "status");
        this.responseStatusCode = getIntegerOrNull(json, "response_status_code", "responseStatusCode");
        this.responseTimeMs = getIntegerOrNull(json, "response_time_ms", "responseTimeMs");
        this.errorMessage = getStringOrNull(json, "error_message", "errorMessage");
        this.errorCode = getStringOrNull(json, "error_code", "errorCode");
        this.nextRetryAt = parseInstant(getStringOrNull(json, "next_retry_at", "nextRetryAt"));
        this.createdAt = parseInstant(getStringOrNull(json, "created_at", "createdAt"));
        this.deliveredAt = parseInstant(getStringOrNull(json, "delivered_at", "deliveredAt"));
    }

    private String getStringOrNull(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
    }

    private String getStringOrNull(JsonObject json, String key1, String key2) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsString();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsString();
        return null;
    }

    private int getIntOrDefault(JsonObject json, String key1, String key2, int defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsInt();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsInt();
        return defaultVal;
    }

    private Integer getIntegerOrNull(JsonObject json, String key1, String key2) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsInt();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsInt();
        return null;
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
    public String getWebhookId() { return webhookId; }
    public String getEventId() { return eventId; }
    public String getEventType() { return eventType; }
    public int getAttemptNumber() { return attemptNumber; }
    public int getMaxAttempts() { return maxAttempts; }
    public String getStatus() { return status; }
    public Integer getResponseStatusCode() { return responseStatusCode; }
    public Integer getResponseTimeMs() { return responseTimeMs; }
    public String getErrorMessage() { return errorMessage; }
    public String getErrorCode() { return errorCode; }
    public Instant getNextRetryAt() { return nextRetryAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getDeliveredAt() { return deliveredAt; }

    public boolean isDelivered() { return STATUS_DELIVERED.equals(status); }
    public boolean isFailed() { return STATUS_FAILED.equals(status); }

    @Override
    public String toString() {
        return "WebhookDelivery{id='" + id + "', eventType='" + eventType + "', status='" + status + "'}";
    }
}
