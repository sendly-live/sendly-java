package com.sendly.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UpdateCampaignRequest {
    private final String name;
    private final String text;
    @SerializedName("contact_list_ids")
    private final List<String> contactListIds;
    @SerializedName("template_id")
    private final String templateId;

    private UpdateCampaignRequest(Builder builder) {
        this.name = builder.name;
        this.text = builder.text;
        this.contactListIds = builder.contactListIds;
        this.templateId = builder.templateId;
    }

    public String getName() { return name; }
    public String getText() { return text; }
    public List<String> getContactListIds() { return contactListIds; }
    public String getTemplateId() { return templateId; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String text;
        private List<String> contactListIds;
        private String templateId;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder contactListIds(List<String> contactListIds) {
            this.contactListIds = contactListIds;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public UpdateCampaignRequest build() {
            return new UpdateCampaignRequest(this);
        }
    }
}
