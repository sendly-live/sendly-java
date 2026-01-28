package com.sendly.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CampaignPreview {
    @SerializedName("recipient_count")
    private int recipientCount;
    @SerializedName("estimated_credits")
    private double estimatedCredits;
    @SerializedName("estimated_cost")
    private double estimatedCost;

    public CampaignPreview() {}

    public CampaignPreview(JsonObject json) {
        if (json.has("recipient_count")) this.recipientCount = json.get("recipient_count").getAsInt();
        if (json.has("estimated_credits")) this.estimatedCredits = json.get("estimated_credits").getAsDouble();
        if (json.has("estimated_cost")) this.estimatedCost = json.get("estimated_cost").getAsDouble();
    }

    public int getRecipientCount() { return recipientCount; }
    public double getEstimatedCredits() { return estimatedCredits; }
    public double getEstimatedCost() { return estimatedCost; }
}
