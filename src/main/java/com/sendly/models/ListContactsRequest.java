package com.sendly.models;

import java.util.HashMap;
import java.util.Map;

public class ListContactsRequest {
    private final Integer limit;
    private final Integer offset;
    private final String search;
    private final String listId;

    private ListContactsRequest(Builder builder) {
        this.limit = builder.limit;
        this.offset = builder.offset;
        this.search = builder.search;
        this.listId = builder.listId;
    }

    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        if (limit != null) {
            params.put("limit", String.valueOf(Math.min(limit, 100)));
        }
        if (offset != null) {
            params.put("offset", String.valueOf(offset));
        }
        if (search != null) {
            params.put("search", search);
        }
        if (listId != null) {
            params.put("list_id", listId);
        }
        return params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer limit;
        private Integer offset;
        private String search;
        private String listId;

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder search(String search) {
            this.search = search;
            return this;
        }

        public Builder listId(String listId) {
            this.listId = listId;
            return this;
        }

        public ListContactsRequest build() {
            return new ListContactsRequest(this);
        }
    }
}
