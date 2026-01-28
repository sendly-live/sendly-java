package com.sendly.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class UpdateContactRequest {
    @SerializedName("phone_number")
    private final String phoneNumber;
    private final String name;
    private final String email;
    private final Map<String, Object> metadata;

    private UpdateContactRequest(Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.name = builder.name;
        this.email = builder.email;
        this.metadata = builder.metadata;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Map<String, Object> getMetadata() { return metadata; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String phoneNumber;
        private String name;
        private String email;
        private Map<String, Object> metadata;

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public UpdateContactRequest build() {
            return new UpdateContactRequest(this);
        }
    }
}
