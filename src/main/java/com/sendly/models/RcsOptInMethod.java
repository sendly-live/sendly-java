package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * One way recipients opt in to messages from an agent, listed in
 * {@link RcsConsentSettings#getOptInMethods()}.
 */
public class RcsOptInMethod {
    private final String methodType;
    private final String description;

    /**
     * @param methodType  One of {@link RcsOptInMethodType}
     * @param description Where and how the opt-in happens
     */
    public RcsOptInMethod(String methodType, String description) {
        this.methodType = methodType;
        this.description = description;
    }

    public RcsOptInMethod(JsonObject json) {
        this.methodType = RcsJson.string(json, "methodType");
        this.description = RcsJson.string(json, "description");
    }

    public String getMethodType() { return methodType; }
    public String getDescription() { return description; }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "methodType", methodType);
        RcsJson.put(o, "description", description);
        return o;
    }
}
