package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Response when creating a webhook (includes secret once).
 */
public class WebhookCreatedResponse extends Webhook {
    private final String secret;

    public WebhookCreatedResponse(JsonObject json) {
        super(json);
        this.secret = json.has("secret") && !json.get("secret").isJsonNull()
            ? json.get("secret").getAsString() : null;
    }

    /**
     * Get the webhook signing secret (only shown once at creation!).
     */
    public String getSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "WebhookCreatedResponse{id='" + getId() + "', url='" + getUrl() + "', secret='***'}";
    }
}
