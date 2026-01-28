package com.sendly.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.ArrayList;

public class Campaign {
    private String id;
    private String name;
    private String text;
    @SerializedName("template_id")
    private String templateId;
    @SerializedName("contact_list_ids")
    private List<String> contactListIds;
    private String status;
    @SerializedName("recipient_count")
    private Integer recipientCount;
    @SerializedName("sent_count")
    private Integer sentCount;
    @SerializedName("delivered_count")
    private Integer deliveredCount;
    @SerializedName("failed_count")
    private Integer failedCount;
    @SerializedName("estimated_credits")
    private Double estimatedCredits;
    @SerializedName("credits_used")
    private Double creditsUsed;
    @SerializedName("scheduled_at")
    private String scheduledAt;
    private String timezone;
    @SerializedName("started_at")
    private String startedAt;
    @SerializedName("completed_at")
    private String completedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public Campaign() {}

    public Campaign(JsonObject json) {
        if (json.has("id")) this.id = json.get("id").getAsString();
        if (json.has("name")) this.name = json.get("name").getAsString();
        if (json.has("text")) this.text = json.get("text").getAsString();
        if (json.has("template_id") && !json.get("template_id").isJsonNull()) {
            this.templateId = json.get("template_id").getAsString();
        }
        if (json.has("contact_list_ids") && json.get("contact_list_ids").isJsonArray()) {
            this.contactListIds = new ArrayList<>();
            json.get("contact_list_ids").getAsJsonArray().forEach(e -> contactListIds.add(e.getAsString()));
        }
        if (json.has("status")) this.status = json.get("status").getAsString();
        if (json.has("recipient_count")) this.recipientCount = json.get("recipient_count").getAsInt();
        if (json.has("sent_count")) this.sentCount = json.get("sent_count").getAsInt();
        if (json.has("delivered_count")) this.deliveredCount = json.get("delivered_count").getAsInt();
        if (json.has("failed_count")) this.failedCount = json.get("failed_count").getAsInt();
        if (json.has("estimated_credits") && !json.get("estimated_credits").isJsonNull()) {
            this.estimatedCredits = json.get("estimated_credits").getAsDouble();
        }
        if (json.has("credits_used") && !json.get("credits_used").isJsonNull()) {
            this.creditsUsed = json.get("credits_used").getAsDouble();
        }
        if (json.has("scheduled_at") && !json.get("scheduled_at").isJsonNull()) {
            this.scheduledAt = json.get("scheduled_at").getAsString();
        }
        if (json.has("timezone") && !json.get("timezone").isJsonNull()) {
            this.timezone = json.get("timezone").getAsString();
        }
        if (json.has("started_at") && !json.get("started_at").isJsonNull()) {
            this.startedAt = json.get("started_at").getAsString();
        }
        if (json.has("completed_at") && !json.get("completed_at").isJsonNull()) {
            this.completedAt = json.get("completed_at").getAsString();
        }
        if (json.has("created_at")) this.createdAt = json.get("created_at").getAsString();
        if (json.has("updated_at")) this.updatedAt = json.get("updated_at").getAsString();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getText() { return text; }
    public String getTemplateId() { return templateId; }
    public List<String> getContactListIds() { return contactListIds; }
    public String getStatus() { return status; }
    public Integer getRecipientCount() { return recipientCount; }
    public Integer getSentCount() { return sentCount; }
    public Integer getDeliveredCount() { return deliveredCount; }
    public Integer getFailedCount() { return failedCount; }
    public Double getEstimatedCredits() { return estimatedCredits; }
    public Double getCreditsUsed() { return creditsUsed; }
    public String getScheduledAt() { return scheduledAt; }
    public String getTimezone() { return timezone; }
    public String getStartedAt() { return startedAt; }
    public String getCompletedAt() { return completedAt; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}
