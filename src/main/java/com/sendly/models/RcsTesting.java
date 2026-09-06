package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * How the carrier network can exercise an agent before launch: a public
 * URL where the reviewer can trigger a message, and notes.
 */
public class RcsTesting {
    private final String testUrl;
    private final String messageId;
    private final String additionalInformation;

    private RcsTesting(Builder builder) {
        this.testUrl = builder.testUrl;
        this.messageId = builder.messageId;
        this.additionalInformation = builder.additionalInformation;
    }

    public RcsTesting(JsonObject json) {
        this.testUrl = RcsJson.string(json, "testUrl");
        this.messageId = RcsJson.string(json, "messageId");
        this.additionalInformation = RcsJson.string(json, "additionalInformation");
    }

    /** Where the reviewer can trigger a message from the agent. */
    public String getTestUrl() { return testUrl; }

    /** Id of a message sent to a test device, when recorded. */
    public String getMessageId() { return messageId; }

    /** Notes for the reviewer. */
    public String getAdditionalInformation() { return additionalInformation; }

    /** Serialize to the JSON the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "testUrl", testUrl);
        RcsJson.put(o, "messageId", messageId);
        RcsJson.put(o, "additionalInformation", additionalInformation);
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String testUrl;
        private String messageId;
        private String additionalInformation;

        /** Where the reviewer can trigger a message from the agent. Required to request launch. */
        public Builder testUrl(String testUrl) {
            this.testUrl = testUrl;
            return this;
        }

        /** Id of a message sent to a test device. */
        public Builder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        /** Notes for the reviewer. */
        public Builder additionalInformation(String additionalInformation) {
            this.additionalInformation = additionalInformation;
            return this;
        }

        public RcsTesting build() {
            return new RcsTesting(this);
        }
    }
}
