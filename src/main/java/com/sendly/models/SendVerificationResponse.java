package com.sendly.models;

import com.google.gson.annotations.SerializedName;

public class SendVerificationResponse {
    private String id;
    private String status;
    private String phone;
    @SerializedName("expires_at")
    private String expiresAt;
    private boolean sandbox;
    @SerializedName("sandbox_code")
    private String sandboxCode;
    private String message;

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getPhone() { return phone; }
    public String getExpiresAt() { return expiresAt; }
    public boolean isSandbox() { return sandbox; }
    public String getSandboxCode() { return sandboxCode; }
    public String getMessage() { return message; }
}
