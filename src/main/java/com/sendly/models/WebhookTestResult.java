package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Result of testing a webhook.
 */
public class WebhookTestResult {
    private final boolean success;
    private final Integer statusCode;
    private final Integer responseTimeMs;
    private final String error;

    public WebhookTestResult(JsonObject json) {
        this.success = json.has("success") && json.get("success").getAsBoolean();
        this.statusCode = getIntegerOrNull(json, "status_code", "statusCode");
        this.responseTimeMs = getIntegerOrNull(json, "response_time_ms", "responseTimeMs");
        this.error = getStringOrNull(json, "error");
    }

    private String getStringOrNull(JsonObject json, String key) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
    }

    private Integer getIntegerOrNull(JsonObject json, String key1, String key2) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsInt();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsInt();
        return null;
    }

    public boolean isSuccess() { return success; }
    public Integer getStatusCode() { return statusCode; }
    public Integer getResponseTimeMs() { return responseTimeMs; }
    public String getError() { return error; }

    @Override
    public String toString() {
        return "WebhookTestResult{success=" + success + ", statusCode=" + statusCode + "}";
    }
}
