package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * The website link shown on an agent's info card.
 */
public class RcsAgentWebsiteContact {
    private final String url;
    private final String label;

    /**
     * @param url   Public https URL
     * @param label Label shown next to it (e.g. "Visit us"), or null
     */
    public RcsAgentWebsiteContact(String url, String label) {
        this.url = url;
        this.label = label;
    }

    public RcsAgentWebsiteContact(JsonObject json) {
        this.url = RcsJson.string(json, "url");
        this.label = RcsJson.string(json, "label");
    }

    public String getUrl() { return url; }
    public String getLabel() { return label; }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "url", url);
        RcsJson.put(o, "label", label);
        return o;
    }
}
