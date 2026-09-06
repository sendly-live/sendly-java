package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * One kind of conversation an agent will have with recipients, listed in
 * {@link RcsCampaign#getInteractions()}.
 */
public class RcsInteraction {
    private final String interactionType;
    private final String description;

    /**
     * @param interactionType One of {@link RcsInteractionType}
     * @param description     What the interaction looks like in practice
     */
    public RcsInteraction(String interactionType, String description) {
        this.interactionType = interactionType;
        this.description = description;
    }

    public RcsInteraction(JsonObject json) {
        this.interactionType = RcsJson.string(json, "interactionType");
        this.description = RcsJson.string(json, "description");
    }

    public String getInteractionType() { return interactionType; }
    public String getDescription() { return description; }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "interactionType", interactionType);
        RcsJson.put(o, "description", description);
        return o;
    }
}
