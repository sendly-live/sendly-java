package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * The email address shown on an agent's info card.
 */
public class RcsAgentEmailContact {
    private final String address;
    private final String label;

    /**
     * @param address Email address
     * @param label   Label shown next to it (e.g. "Email us"), or null
     */
    public RcsAgentEmailContact(String address, String label) {
        this.address = address;
        this.label = label;
    }

    public RcsAgentEmailContact(JsonObject json) {
        this.address = RcsJson.string(json, "address");
        this.label = RcsJson.string(json, "label");
    }

    public String getAddress() { return address; }
    public String getLabel() { return label; }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "address", address);
        RcsJson.put(o, "label", label);
        return o;
    }
}
