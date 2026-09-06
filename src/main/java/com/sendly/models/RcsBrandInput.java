package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * The business identity fields of an RCS brand, as sent to
 * {@code rcs().brands().create(...)} and {@code rcs().brands().update(...)},
 * and as prefilled by {@code rcs().dossier().get()}.
 * <p>
 * Every field is optional while drafting; required-field checks run when the
 * agent is submitted for review. On an update only the fields you set are
 * changed. The address must name {@code countryCode} "US": RCS registration
 * is available to US businesses for now (422 {@code rcs_us_only}).
 * </p>
 *
 * <pre>{@code
 * RcsBrandInput input = RcsBrandInput.builder()
 *     .displayName("Acme")
 *     .legalName("Acme Holdings LLC")
 *     .legalEntityType(RcsLegalEntityType.LIMITED_LIABILITY_COMPANY)
 *     .organizationType(RcsOrganizationType.PRIVATE_PROFIT)
 *     .websiteUrl("https://acme.example")
 *     .ein("12-3456789")
 *     .address(RcsBrandAddress.builder()
 *         .line1("1 Main St").city("Chicago").state("IL").postalCode("60601").countryCode("US")
 *         .build())
 *     .contact(RcsBrandContact.builder()
 *         .firstName("Sam").lastName("Lee").email("sam@acme.example").phoneNumber("+13125550100")
 *         .build())
 *     .build();
 * }</pre>
 */
public class RcsBrandInput {
    private final String displayName;
    private final String legalName;
    private final String legalEntityType;
    private final String organizationType;
    private final String websiteUrl;
    private final String ein;
    private final String stockSymbol;
    private final RcsBrandAddress address;
    private final RcsBrandContact contact;

    private RcsBrandInput(Builder builder) {
        this.displayName = builder.displayName;
        this.legalName = builder.legalName;
        this.legalEntityType = builder.legalEntityType;
        this.organizationType = builder.organizationType;
        this.websiteUrl = builder.websiteUrl;
        this.ein = builder.ein;
        this.stockSymbol = builder.stockSymbol;
        this.address = builder.address;
        this.contact = builder.contact;
    }

    /** Decode a brand-input object (e.g. the {@code brand} of a dossier). */
    public RcsBrandInput(JsonObject json) {
        this.displayName = RcsJson.string(json, "displayName");
        this.legalName = RcsJson.string(json, "legalName");
        this.legalEntityType = RcsJson.string(json, "legalEntityType");
        this.organizationType = RcsJson.string(json, "organizationType");
        this.websiteUrl = RcsJson.string(json, "websiteUrl");
        this.ein = RcsJson.string(json, "ein");
        this.stockSymbol = RcsJson.string(json, "stockSymbol");
        JsonObject address = RcsJson.object(json, "address");
        this.address = address != null ? new RcsBrandAddress(address) : null;
        JsonObject contact = RcsJson.object(json, "contact");
        this.contact = contact != null ? new RcsBrandContact(contact) : null;
    }

    public String getDisplayName() { return displayName; }
    public String getLegalName() { return legalName; }
    public String getLegalEntityType() { return legalEntityType; }
    public String getOrganizationType() { return organizationType; }
    public String getWebsiteUrl() { return websiteUrl; }
    public String getEin() { return ein; }
    public String getStockSymbol() { return stockSymbol; }
    public RcsBrandAddress getAddress() { return address; }
    public RcsBrandContact getContact() { return contact; }

    /** Serialize to the JSON body the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "displayName", displayName);
        RcsJson.put(o, "legalName", legalName);
        RcsJson.put(o, "legalEntityType", legalEntityType);
        RcsJson.put(o, "organizationType", organizationType);
        RcsJson.put(o, "websiteUrl", websiteUrl);
        RcsJson.put(o, "ein", ein);
        RcsJson.put(o, "stockSymbol", stockSymbol);
        if (address != null) o.add("address", address.toJson());
        if (contact != null) o.add("contact", contact.toJson());
        return o;
    }

    /** Copy this input into a builder, e.g. to complete a dossier prefill. */
    public Builder toBuilder() {
        return new Builder()
                .displayName(displayName)
                .legalName(legalName)
                .legalEntityType(legalEntityType)
                .organizationType(organizationType)
                .websiteUrl(websiteUrl)
                .ein(ein)
                .stockSymbol(stockSymbol)
                .address(address)
                .contact(contact);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String displayName;
        private String legalName;
        private String legalEntityType;
        private String organizationType;
        private String websiteUrl;
        private String ein;
        private String stockSymbol;
        private RcsBrandAddress address;
        private RcsBrandContact contact;

        /** The brand name recipients see. */
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /** Registered legal business name. */
        public Builder legalName(String legalName) {
            this.legalName = legalName;
            return this;
        }

        /** Legal entity type; see {@link RcsLegalEntityType}. */
        public Builder legalEntityType(String legalEntityType) {
            this.legalEntityType = legalEntityType;
            return this;
        }

        /** Organization type; see {@link RcsOrganizationType}. */
        public Builder organizationType(String organizationType) {
            this.organizationType = organizationType;
            return this;
        }

        /** Business website (https). */
        public Builder websiteUrl(String websiteUrl) {
            this.websiteUrl = websiteUrl;
            return this;
        }

        /** 9-digit EIN, with or without the dash ("12-3456789"). */
        public Builder ein(String ein) {
            this.ein = ein;
            return this;
        }

        /** Stock symbol as "EXCHANGE:TICKER", for publicly traded businesses. */
        public Builder stockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
            return this;
        }

        /** Business address; {@code countryCode} must be "US". */
        public Builder address(RcsBrandAddress address) {
            this.address = address;
            return this;
        }

        /** The person the carrier network can reach about the brand. */
        public Builder contact(RcsBrandContact contact) {
            this.contact = contact;
            return this;
        }

        public RcsBrandInput build() {
            return new RcsBrandInput(this);
        }
    }
}
