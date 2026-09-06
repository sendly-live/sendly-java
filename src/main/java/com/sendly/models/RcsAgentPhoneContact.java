package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * The phone number shown on an agent's info card.
 */
public class RcsAgentPhoneContact {
    private final String number;
    private final String label;

    /**
     * @param number Phone number in E.164 format
     * @param label  Label shown next to it (e.g. "Support"), or null
     */
    public RcsAgentPhoneContact(String number, String label) {
        this.number = number;
        this.label = label;
    }

    public RcsAgentPhoneContact(JsonObject json) {
        this.number = RcsJson.string(json, "number");
        this.label = RcsJson.string(json, "label");
    }

    public String getNumber() { return number; }
    public String getLabel() { return label; }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "number", number);
        RcsJson.put(o, "label", label);
        return o;
    }
}
