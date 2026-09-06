package com.sendly.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * How recipients opt in to, get help with, and opt out of an agent's
 * messages. Part of {@link RcsCampaign}.
 * <p>
 * {@code callToActionMediaUrl} must be a public https URL; files can't be
 * uploaded over the API.
 * </p>
 */
public class RcsConsentSettings {
    private final List<RcsOptInMethod> optInMethods;
    private final String callToAction;
    private final String callToActionUrl;
    private final String callToActionMediaUrl;
    private final Boolean doubleOptIn;
    private final String doubleOptInMessage;
    private final String optInMessage;
    private final String helpResponse;
    private final String optOutResponse;

    private RcsConsentSettings(Builder builder) {
        this.optInMethods = builder.optInMethods;
        this.callToAction = builder.callToAction;
        this.callToActionUrl = builder.callToActionUrl;
        this.callToActionMediaUrl = builder.callToActionMediaUrl;
        this.doubleOptIn = builder.doubleOptIn;
        this.doubleOptInMessage = builder.doubleOptInMessage;
        this.optInMessage = builder.optInMessage;
        this.helpResponse = builder.helpResponse;
        this.optOutResponse = builder.optOutResponse;
    }

    public RcsConsentSettings(JsonObject json) {
        List<JsonObject> methods = RcsJson.objects(json, "optInMethods");
        if (methods != null) {
            this.optInMethods = new ArrayList<>();
            for (JsonObject m : methods) {
                this.optInMethods.add(new RcsOptInMethod(m));
            }
        } else {
            this.optInMethods = null;
        }
        this.callToAction = RcsJson.string(json, "callToAction");
        this.callToActionUrl = RcsJson.string(json, "callToActionUrl");
        this.callToActionMediaUrl = RcsJson.string(json, "callToActionMediaUrl");
        this.doubleOptIn = RcsJson.bool(json, "doubleOptIn");
        this.doubleOptInMessage = RcsJson.string(json, "doubleOptInMessage");
        this.optInMessage = RcsJson.string(json, "optInMessage");
        this.helpResponse = RcsJson.string(json, "helpResponse");
        this.optOutResponse = RcsJson.string(json, "optOutResponse");
    }

    /** How recipients opt in, or null. */
    public List<RcsOptInMethod> getOptInMethods() { return optInMethods; }

    /** The call to action recipients see when opting in. */
    public String getCallToAction() { return callToAction; }

    /** Where the call to action lives (https). */
    public String getCallToActionUrl() { return callToActionUrl; }

    /** Public https URL of a screenshot of the opt-in. */
    public String getCallToActionMediaUrl() { return callToActionMediaUrl; }

    /** Whether a confirmation reply is required; null when unset. */
    public Boolean getDoubleOptIn() { return doubleOptIn; }

    /** The confirmation prompt sent when double opt-in is on. */
    public String getDoubleOptInMessage() { return doubleOptInMessage; }

    /** The message sent once a recipient opts in. */
    public String getOptInMessage() { return optInMessage; }

    /** The reply to HELP. */
    public String getHelpResponse() { return helpResponse; }

    /** The reply to STOP. */
    public String getOptOutResponse() { return optOutResponse; }

    /** Serialize to the JSON the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        if (optInMethods != null) {
            JsonArray arr = new JsonArray();
            for (RcsOptInMethod m : optInMethods) {
                if (m != null) arr.add(m.toJson());
            }
            o.add("optInMethods", arr);
        }
        RcsJson.put(o, "callToAction", callToAction);
        RcsJson.put(o, "callToActionUrl", callToActionUrl);
        RcsJson.put(o, "callToActionMediaUrl", callToActionMediaUrl);
        RcsJson.put(o, "doubleOptIn", doubleOptIn);
        RcsJson.put(o, "doubleOptInMessage", doubleOptInMessage);
        RcsJson.put(o, "optInMessage", optInMessage);
        RcsJson.put(o, "helpResponse", helpResponse);
        RcsJson.put(o, "optOutResponse", optOutResponse);
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<RcsOptInMethod> optInMethods;
        private String callToAction;
        private String callToActionUrl;
        private String callToActionMediaUrl;
        private Boolean doubleOptIn;
        private String doubleOptInMessage;
        private String optInMessage;
        private String helpResponse;
        private String optOutResponse;

        /** How recipients opt in. */
        public Builder optInMethods(List<RcsOptInMethod> optInMethods) {
            this.optInMethods = optInMethods;
            return this;
        }

        /** The call to action recipients see when opting in. */
        public Builder callToAction(String callToAction) {
            this.callToAction = callToAction;
            return this;
        }

        /** Where the call to action lives (https). */
        public Builder callToActionUrl(String callToActionUrl) {
            this.callToActionUrl = callToActionUrl;
            return this;
        }

        /** Public https URL of a screenshot of the opt-in. Files can't be uploaded over the API. */
        public Builder callToActionMediaUrl(String callToActionMediaUrl) {
            this.callToActionMediaUrl = callToActionMediaUrl;
            return this;
        }

        /** Whether a confirmation reply is required. */
        public Builder doubleOptIn(Boolean doubleOptIn) {
            this.doubleOptIn = doubleOptIn;
            return this;
        }

        /** The confirmation prompt sent when double opt-in is on. */
        public Builder doubleOptInMessage(String doubleOptInMessage) {
            this.doubleOptInMessage = doubleOptInMessage;
            return this;
        }

        /** The message sent once a recipient opts in. */
        public Builder optInMessage(String optInMessage) {
            this.optInMessage = optInMessage;
            return this;
        }

        /** The reply to HELP. */
        public Builder helpResponse(String helpResponse) {
            this.helpResponse = helpResponse;
            return this;
        }

        /** The reply to STOP. */
        public Builder optOutResponse(String optOutResponse) {
            this.optOutResponse = optOutResponse;
            return this;
        }

        public RcsConsentSettings build() {
            return new RcsConsentSettings(this);
        }
    }
}
