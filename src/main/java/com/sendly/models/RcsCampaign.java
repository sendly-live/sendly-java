package com.sendly.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * What an agent will send and how recipients consent to it. Needed before
 * {@code rcs().agents().requestLaunch(...)}: an agent overview, at least one
 * interaction, at least three message examples, and consent settings.
 */
public class RcsCampaign {
    private final String companyOverview;
    private final String agentOverview;
    private final String additionalInformation;
    private final List<RcsInteraction> interactions;
    private final List<String> messageExamples;
    private final RcsConsentSettings consentSettings;

    private RcsCampaign(Builder builder) {
        this.companyOverview = builder.companyOverview;
        this.agentOverview = builder.agentOverview;
        this.additionalInformation = builder.additionalInformation;
        this.interactions = builder.interactions;
        this.messageExamples = builder.messageExamples;
        this.consentSettings = builder.consentSettings;
    }

    public RcsCampaign(JsonObject json) {
        this.companyOverview = RcsJson.string(json, "companyOverview");
        this.agentOverview = RcsJson.string(json, "agentOverview");
        this.additionalInformation = RcsJson.string(json, "additionalInformation");
        List<JsonObject> interactions = RcsJson.objects(json, "interactions");
        if (interactions != null) {
            this.interactions = new ArrayList<>();
            for (JsonObject i : interactions) {
                this.interactions.add(new RcsInteraction(i));
            }
        } else {
            this.interactions = null;
        }
        this.messageExamples = RcsJson.strings(json, "messageExamples");
        JsonObject consent = RcsJson.object(json, "consentSettings");
        this.consentSettings = consent != null ? new RcsConsentSettings(consent) : null;
    }

    /** What the business does. */
    public String getCompanyOverview() { return companyOverview; }

    /** What the agent is for. */
    public String getAgentOverview() { return agentOverview; }

    /** Anything else the reviewer should know. */
    public String getAdditionalInformation() { return additionalInformation; }

    /** The kinds of conversation the agent will have, or null. */
    public List<RcsInteraction> getInteractions() { return interactions; }

    /** Example messages the agent will send, or null. */
    public List<String> getMessageExamples() { return messageExamples; }

    /** Opt-in, HELP and STOP handling, or null. */
    public RcsConsentSettings getConsentSettings() { return consentSettings; }

    /** Serialize to the JSON the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "companyOverview", companyOverview);
        RcsJson.put(o, "agentOverview", agentOverview);
        RcsJson.put(o, "additionalInformation", additionalInformation);
        if (interactions != null) {
            JsonArray arr = new JsonArray();
            for (RcsInteraction i : interactions) {
                if (i != null) arr.add(i.toJson());
            }
            o.add("interactions", arr);
        }
        RcsJson.put(o, "messageExamples", RcsJson.stringArray(messageExamples));
        if (consentSettings != null) o.add("consentSettings", consentSettings.toJson());
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String companyOverview;
        private String agentOverview;
        private String additionalInformation;
        private List<RcsInteraction> interactions;
        private List<String> messageExamples;
        private RcsConsentSettings consentSettings;

        /** What the business does. */
        public Builder companyOverview(String companyOverview) {
            this.companyOverview = companyOverview;
            return this;
        }

        /** What the agent is for. Required to request launch. */
        public Builder agentOverview(String agentOverview) {
            this.agentOverview = agentOverview;
            return this;
        }

        /** Anything else the reviewer should know. */
        public Builder additionalInformation(String additionalInformation) {
            this.additionalInformation = additionalInformation;
            return this;
        }

        /** The kinds of conversation the agent will have. At least one to request launch. */
        public Builder interactions(List<RcsInteraction> interactions) {
            this.interactions = interactions;
            return this;
        }

        /** Example messages the agent will send. At least three to request launch. */
        public Builder messageExamples(List<String> messageExamples) {
            this.messageExamples = messageExamples;
            return this;
        }

        /** Opt-in, HELP and STOP handling. Required to request launch. */
        public Builder consentSettings(RcsConsentSettings consentSettings) {
            this.consentSettings = consentSettings;
            return this;
        }

        public RcsCampaign build() {
            return new RcsCampaign(this);
        }
    }
}
