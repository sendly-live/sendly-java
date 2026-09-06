package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * A brand's business address. Used both when drafting a brand
 * ({@link RcsBrandInput}) and when reading one back ({@link RcsBrand}).
 * <p>
 * RCS registration is available to US businesses for now, so
 * {@code countryCode} must be {@code "US"}.
 * </p>
 */
public class RcsBrandAddress {
    private final String line1;
    private final String line2;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String countryCode;

    private RcsBrandAddress(Builder builder) {
        this.line1 = builder.line1;
        this.line2 = builder.line2;
        this.city = builder.city;
        this.state = builder.state;
        this.postalCode = builder.postalCode;
        this.countryCode = builder.countryCode;
    }

    public RcsBrandAddress(JsonObject json) {
        this.line1 = RcsJson.string(json, "line1");
        this.line2 = RcsJson.string(json, "line2");
        this.city = RcsJson.string(json, "city");
        this.state = RcsJson.string(json, "state");
        this.postalCode = RcsJson.string(json, "postalCode");
        this.countryCode = RcsJson.string(json, "countryCode");
    }

    /** Street address, first line. */
    public String getLine1() { return line1; }

    /** Street address, second line, or null. */
    public String getLine2() { return line2; }

    /** City. */
    public String getCity() { return city; }

    /** State, as a two-letter code. */
    public String getState() { return state; }

    /** Postal code. */
    public String getPostalCode() { return postalCode; }

    /** ISO 3166-1 alpha-2 country code; {@code "US"}. */
    public String getCountryCode() { return countryCode; }

    /** Serialize to the JSON the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "line1", line1);
        RcsJson.put(o, "line2", line2);
        RcsJson.put(o, "city", city);
        RcsJson.put(o, "state", state);
        RcsJson.put(o, "postalCode", postalCode);
        RcsJson.put(o, "countryCode", countryCode);
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String postalCode;
        private String countryCode;

        /** Street address, first line. */
        public Builder line1(String line1) {
            this.line1 = line1;
            return this;
        }

        /** Street address, second line. */
        public Builder line2(String line2) {
            this.line2 = line2;
            return this;
        }

        /** City. */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /** State, as a two-letter code (e.g. "IL"). */
        public Builder state(String state) {
            this.state = state;
            return this;
        }

        /** Postal code. */
        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /** ISO 3166-1 alpha-2 country code. Must be "US". */
        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public RcsBrandAddress build() {
            return new RcsBrandAddress(this);
        }
    }
}
