package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * The person the carrier network can reach about a brand. Used both when
 * drafting a brand ({@link RcsBrandInput}) and when reading one back
 * ({@link RcsBrand}).
 */
public class RcsBrandContact {
    private final String firstName;
    private final String lastName;
    private final String title;
    private final String email;
    private final String phoneNumber;

    private RcsBrandContact(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.title = builder.title;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
    }

    public RcsBrandContact(JsonObject json) {
        this.firstName = RcsJson.string(json, "firstName");
        this.lastName = RcsJson.string(json, "lastName");
        this.title = RcsJson.string(json, "title");
        this.email = RcsJson.string(json, "email");
        this.phoneNumber = RcsJson.string(json, "phoneNumber");
    }

    /** First name. */
    public String getFirstName() { return firstName; }

    /** Last name. */
    public String getLastName() { return lastName; }

    /** Job title, or null. */
    public String getTitle() { return title; }

    /** Email address. */
    public String getEmail() { return email; }

    /** Phone number in E.164 format. */
    public String getPhoneNumber() { return phoneNumber; }

    /** Serialize to the JSON the API expects. Unset fields are omitted. */
    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "firstName", firstName);
        RcsJson.put(o, "lastName", lastName);
        RcsJson.put(o, "title", title);
        RcsJson.put(o, "email", email);
        RcsJson.put(o, "phoneNumber", phoneNumber);
        return o;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String title;
        private String email;
        private String phoneNumber;

        /** First name. */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /** Last name. */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /** Job title. */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /** Email address. */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        /** Phone number in E.164 format (e.g. +13125550100). */
        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public RcsBrandContact build() {
            return new RcsBrandContact(this);
        }
    }
}
