package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Request body for {@code rcs().agents().update()}.
 * <p>
 * Only the groups you set are changed: {@code displayName}, {@code useCase}
 * and {@code basics} are merged into the agent's basics, and
 * {@code campaign} and {@code testing} are merged section-wise. Basics lock
 * once they have been sent to the carrier network; campaign and testing
 * lock once the launch has been sent, unless the launch was rejected.
 * Logo, hero and call-to-action media must be public https URLs.
 * </p>
 */
public class UpdateRcsAgentRequest {
    private final String displayName;
    private final String useCase;
    private final RcsAgentBasics basics;
    private final RcsCampaign campaign;
    private final RcsTesting testing;

    private UpdateRcsAgentRequest(Builder builder) {
        this.displayName = builder.displayName;
        this.useCase = builder.useCase;
        this.basics = builder.basics;
        this.campaign = builder.campaign;
        this.testing = builder.testing;
    }

    public String getDisplayName() { return displayName; }
    public String getUseCase() { return useCase; }
    public RcsAgentBasics getBasics() { return basics; }
    public RcsCampaign getCampaign() { return campaign; }
    public RcsTesting getTesting() { return testing; }

    /** Serialize to the JSON body the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "displayName", displayName);
        RcsJson.put(o, "useCase", useCase);
        if (basics != null) o.add("basics", basics.toJson());
        if (campaign != null) o.add("campaign", campaign.toJson());
        if (testing != null) o.add("testing", testing.toJson());
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String displayName;
        private String useCase;
        private RcsAgentBasics basics;
        private RcsCampaign campaign;
        private RcsTesting testing;

        /** The agent name recipients see. */
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /** Use case; see {@link RcsAgentUseCase}. */
        public Builder useCase(String useCase) {
            this.useCase = useCase;
            return this;
        }

        /** Identity fields to merge into the agent's basics. */
        public Builder basics(RcsAgentBasics basics) {
            this.basics = basics;
            return this;
        }

        /** Campaign fields to merge. */
        public Builder campaign(RcsCampaign campaign) {
            this.campaign = campaign;
            return this;
        }

        /** Testing fields to merge. */
        public Builder testing(RcsTesting testing) {
            this.testing = testing;
            return this;
        }

        public UpdateRcsAgentRequest build() {
            return new UpdateRcsAgentRequest(this);
        }
    }
}
