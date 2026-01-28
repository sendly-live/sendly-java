package com.sendly.models;

import java.util.HashMap;
import java.util.Map;

public class ListCampaignsRequest {
    private final Integer limit;
    private final Integer offset;
    private final String status;

    private ListCampaignsRequest(Builder builder) {
        this.limit = builder.limit;
        this.offset = builder.offset;
        this.status = builder.status;
    }

    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        if (limit != null) {
            params.put("limit", String.valueOf(Math.min(limit, 100)));
        }
        if (offset != null) {
            params.put("offset", String.valueOf(offset));
        }
        if (status != null) {
            params.put("status", status);
        }
        return params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer limit;
        private Integer offset;
        private String status;

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public ListCampaignsRequest build() {
            return new ListCampaignsRequest(this);
        }
    }
}
