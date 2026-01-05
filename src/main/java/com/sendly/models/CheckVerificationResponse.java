package com.sendly.models;

import com.google.gson.annotations.SerializedName;

public class CheckVerificationResponse {
    private String id;
    private String status;
    private String phone;
    @SerializedName("verified_at")
    private String verifiedAt;
    @SerializedName("remaining_attempts")
    private Integer remainingAttempts;

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getPhone() { return phone; }
    public String getVerifiedAt() { return verifiedAt; }
    public Integer getRemainingAttempts() { return remainingAttempts; }
}
