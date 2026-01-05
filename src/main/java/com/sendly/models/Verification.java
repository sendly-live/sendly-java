package com.sendly.models;

import com.google.gson.annotations.SerializedName;

public class Verification {
    private String id;
    private String status;
    private String phone;
    @SerializedName("delivery_status")
    private String deliveryStatus;
    private int attempts;
    @SerializedName("max_attempts")
    private int maxAttempts;
    @SerializedName("expires_at")
    private String expiresAt;
    @SerializedName("verified_at")
    private String verifiedAt;
    @SerializedName("created_at")
    private String createdAt;
    private boolean sandbox;
    @SerializedName("app_name")
    private String appName;
    @SerializedName("template_id")
    private String templateId;
    @SerializedName("profile_id")
    private String profileId;

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getPhone() { return phone; }
    public String getDeliveryStatus() { return deliveryStatus; }
    public int getAttempts() { return attempts; }
    public int getMaxAttempts() { return maxAttempts; }
    public String getExpiresAt() { return expiresAt; }
    public String getVerifiedAt() { return verifiedAt; }
    public String getCreatedAt() { return createdAt; }
    public boolean isSandbox() { return sandbox; }
    public String getAppName() { return appName; }
    public String getTemplateId() { return templateId; }
    public String getProfileId() { return profileId; }
}
