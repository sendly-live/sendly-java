package com.sendly.resources;

import com.google.gson.JsonObject;
import com.sendly.Sendly;
import com.sendly.exceptions.SendlyException;
import com.sendly.models.Credits;

import java.util.HashMap;
import java.util.Map;

/**
 * Account resource for accessing account information and credits.
 */
public class AccountResource {
    private final Sendly client;

    public AccountResource(Sendly client) {
        this.client = client;
    }

    /**
     * Get credit balance.
     */
    public Credits getCredits() throws SendlyException {
        JsonObject response = client.get("/credits", null);
        return new Credits(response);
    }

    /**
     * Get API key usage statistics.
     */
    public JsonObject getApiKeyUsage(String keyId) throws SendlyException {
        return client.get("/keys/" + keyId + "/usage", null);
    }
}
