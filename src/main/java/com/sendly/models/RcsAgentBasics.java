package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * An agent's identity: what recipients see on the agent's info card. Used
 * both when drafting ({@link CreateRcsAgentRequest},
 * {@link UpdateRcsAgentRequest}) and when reading an agent back
 * ({@link RcsAgentDetails#getBasics()}).
 * <p>
 * Assets can't be uploaded over the API: {@code logoUrl} and {@code heroUrl}
 * must already be public https URLs. Upload files from the dashboard instead.
 * </p>
 */
public class RcsAgentBasics {
    private final String displayName;
    private final String useCase;
    private final String hostingRegion;
    private final String description;
    private final String logoUrl;
    private final String heroUrl;
    private final String brandColor;
    private final String privacyPolicyUrl;
    private final String termsAndConditionsUrl;
    private final RcsAgentPhoneContact phoneNumber;
    private final RcsAgentWebsiteContact website;
    private final RcsAgentEmailContact email;

    private RcsAgentBasics(Builder builder) {
        this.displayName = builder.displayName;
        this.useCase = builder.useCase;
        this.hostingRegion = null;
        this.description = builder.description;
        this.logoUrl = builder.logoUrl;
        this.heroUrl = builder.heroUrl;
        this.brandColor = builder.brandColor;
        this.privacyPolicyUrl = builder.privacyPolicyUrl;
        this.termsAndConditionsUrl = builder.termsAndConditionsUrl;
        this.phoneNumber = builder.phoneNumber;
        this.website = builder.website;
        this.email = builder.email;
    }

    public RcsAgentBasics(JsonObject json) {
        this.displayName = RcsJson.string(json, "displayName");
        this.useCase = RcsJson.string(json, "useCase");
        this.hostingRegion = RcsJson.string(json, "hostingRegion");
        this.description = RcsJson.string(json, "description");
        this.logoUrl = RcsJson.string(json, "logoUrl");
        this.heroUrl = RcsJson.string(json, "heroUrl");
        this.brandColor = RcsJson.string(json, "brandColor");
        this.privacyPolicyUrl = RcsJson.string(json, "privacyPolicyUrl");
        this.termsAndConditionsUrl = RcsJson.string(json, "termsAndConditionsUrl");
        JsonObject phone = RcsJson.object(json, "phoneNumber");
        this.phoneNumber = phone != null ? new RcsAgentPhoneContact(phone) : null;
        JsonObject website = RcsJson.object(json, "website");
        this.website = website != null ? new RcsAgentWebsiteContact(website) : null;
        JsonObject email = RcsJson.object(json, "email");
        this.email = email != null ? new RcsAgentEmailContact(email) : null;
    }

    /** The agent name recipients see. */
    public String getDisplayName() { return displayName; }

    /** Use case; see {@link RcsAgentUseCase}. */
    public String getUseCase() { return useCase; }

    /** Hosting region, set by Sendly; null on a draft. */
    public String getHostingRegion() { return hostingRegion; }

    /** Short description shown on the info card. */
    public String getDescription() { return description; }

    /** Public https URL of the logo. */
    public String getLogoUrl() { return logoUrl; }

    /** Public https URL of the hero image. */
    public String getHeroUrl() { return heroUrl; }

    /** Brand colour as "#RGB" or "#RRGGBB". */
    public String getBrandColor() { return brandColor; }

    /** Privacy policy URL (https). */
    public String getPrivacyPolicyUrl() { return privacyPolicyUrl; }

    /** Terms and conditions URL (https). */
    public String getTermsAndConditionsUrl() { return termsAndConditionsUrl; }

    /** Phone contact on the info card, or null. */
    public RcsAgentPhoneContact getPhoneNumber() { return phoneNumber; }

    /** Website contact on the info card, or null. */
    public RcsAgentWebsiteContact getWebsite() { return website; }

    /** Email contact on the info card, or null. */
    public RcsAgentEmailContact getEmail() { return email; }

    /** Serialize to the JSON the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "displayName", displayName);
        RcsJson.put(o, "useCase", useCase);
        RcsJson.put(o, "description", description);
        RcsJson.put(o, "logoUrl", logoUrl);
        RcsJson.put(o, "heroUrl", heroUrl);
        RcsJson.put(o, "brandColor", brandColor);
        RcsJson.put(o, "privacyPolicyUrl", privacyPolicyUrl);
        RcsJson.put(o, "termsAndConditionsUrl", termsAndConditionsUrl);
        if (phoneNumber != null) o.add("phoneNumber", phoneNumber.toJson());
        if (website != null) o.add("website", website.toJson());
        if (email != null) o.add("email", email.toJson());
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String displayName;
        private String useCase;
        private String description;
        private String logoUrl;
        private String heroUrl;
        private String brandColor;
        private String privacyPolicyUrl;
        private String termsAndConditionsUrl;
        private RcsAgentPhoneContact phoneNumber;
        private RcsAgentWebsiteContact website;
        private RcsAgentEmailContact email;

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

        /** Short description shown on the info card. */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /** Public https URL of the logo. Files can't be uploaded over the API. */
        public Builder logoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        /** Public https URL of the hero image. Files can't be uploaded over the API. */
        public Builder heroUrl(String heroUrl) {
            this.heroUrl = heroUrl;
            return this;
        }

        /** Brand colour as "#RGB" or "#RRGGBB". */
        public Builder brandColor(String brandColor) {
            this.brandColor = brandColor;
            return this;
        }

        /** Privacy policy URL (https). */
        public Builder privacyPolicyUrl(String privacyPolicyUrl) {
            this.privacyPolicyUrl = privacyPolicyUrl;
            return this;
        }

        /** Terms and conditions URL (https). */
        public Builder termsAndConditionsUrl(String termsAndConditionsUrl) {
            this.termsAndConditionsUrl = termsAndConditionsUrl;
            return this;
        }

        /** Phone contact shown on the info card. */
        public Builder phoneNumber(RcsAgentPhoneContact phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /** Website link shown on the info card. */
        public Builder website(RcsAgentWebsiteContact website) {
            this.website = website;
            return this;
        }

        /** Email contact shown on the info card. */
        public Builder email(RcsAgentEmailContact email) {
            this.email = email;
            return this;
        }

        public RcsAgentBasics build() {
            return new RcsAgentBasics(this);
        }
    }
}
