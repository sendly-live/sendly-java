package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Request body for {@code rcs().agents().create()}.
 * <p>
 * Only {@code brandId} is required to start a draft. {@code displayName} and
 * {@code useCase} set here override the same fields inside {@code basics}.
 * Logo, hero and call-to-action media must be public https URLs; files
 * can't be uploaded over the API.
 * </p>
 *
 * <pre>{@code
 * CreateRcsAgentRequest request = CreateRcsAgentRequest.builder()
 *     .brandId(brand.getId())
 *     .displayName("Acme")
 *     .useCase(RcsAgentUseCase.TRANSACTIONAL)
 *     .basics(RcsAgentBasics.builder()
 *         .description("Order updates from Acme")
 *         .logoUrl("https://acme.example/logo.png")
 *         .heroUrl("https://acme.example/hero.png")
 *         .brandColor("#0055FF")
 *         .privacyPolicyUrl("https://acme.example/privacy")
 *         .termsAndConditionsUrl("https://acme.example/terms")
 *         .build())
 *     .build();
 * }</pre>
 */
public class CreateRcsAgentRequest {
    private final String brandId;
    private final String displayName;
    private final String useCase;
    private final RcsAgentBasics basics;
    private final RcsCampaign campaign;
    private final RcsTesting testing;

    private CreateRcsAgentRequest(Builder builder) {
        this.brandId = builder.brandId;
        this.displayName = builder.displayName;
        this.useCase = builder.useCase;
        this.basics = builder.basics;
        this.campaign = builder.campaign;
        this.testing = builder.testing;
    }

    public String getBrandId() { return brandId; }
    public String getDisplayName() { return displayName; }
    public String getUseCase() { return useCase; }
    public RcsAgentBasics getBasics() { return basics; }
    public RcsCampaign getCampaign() { return campaign; }
    public RcsTesting getTesting() { return testing; }

    /** Serialize to the JSON body the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "brandId", brandId);
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
        private String brandId;
        private String displayName;
        private String useCase;
        private RcsAgentBasics basics;
        private RcsCampaign campaign;
        private RcsTesting testing;

        /** The brand the agent belongs to (required). */
        public Builder brandId(String brandId) {
            this.brandId = brandId;
            return this;
        }

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

        /** Identity shown on the info card. */
        public Builder basics(RcsAgentBasics basics) {
            this.basics = basics;
            return this;
        }

        /** Campaign details; can also be added later with {@link UpdateRcsAgentRequest}. */
        public Builder campaign(RcsCampaign campaign) {
            this.campaign = campaign;
            return this;
        }

        /** Testing details; can also be added later with {@link UpdateRcsAgentRequest}. */
        public Builder testing(RcsTesting testing) {
            this.testing = testing;
            return this;
        }

        public CreateRcsAgentRequest build() {
            return new CreateRcsAgentRequest(this);
        }
    }
}
