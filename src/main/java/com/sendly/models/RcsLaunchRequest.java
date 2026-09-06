package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Optional body for {@code rcs().agents().requestLaunch()}. Both fields are
 * stored into the agent's testing section before the launch is requested.
 */
public class RcsLaunchRequest {
    private final String testUrl;
    private final String testingAdditionalInformation;

    private RcsLaunchRequest(Builder builder) {
        this.testUrl = builder.testUrl;
        this.testingAdditionalInformation = builder.testingAdditionalInformation;
    }

    public String getTestUrl() { return testUrl; }
    public String getTestingAdditionalInformation() { return testingAdditionalInformation; }

    /** Serialize to the JSON body the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "testUrl", testUrl);
        RcsJson.put(o, "testingAdditionalInformation", testingAdditionalInformation);
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String testUrl;
        private String testingAdditionalInformation;

        /** Where the reviewer can trigger a message from the agent. */
        public Builder testUrl(String testUrl) {
            this.testUrl = testUrl;
            return this;
        }

        /** Notes for the reviewer. */
        public Builder testingAdditionalInformation(String testingAdditionalInformation) {
            this.testingAdditionalInformation = testingAdditionalInformation;
            return this;
        }

        public RcsLaunchRequest build() {
            return new RcsLaunchRequest(this);
        }
    }
}
