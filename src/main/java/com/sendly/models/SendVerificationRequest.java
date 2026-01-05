package com.sendly.models;

public class SendVerificationRequest {
    private String to;
    private String templateId;
    private String profileId;
    private String appName;
    private Integer timeoutSecs;
    private Integer codeLength;

    public SendVerificationRequest(String to) {
        this.to = to;
    }

    public String getTo() { return to; }
    public SendVerificationRequest setTo(String to) { this.to = to; return this; }
    public String getTemplateId() { return templateId; }
    public SendVerificationRequest setTemplateId(String templateId) { this.templateId = templateId; return this; }
    public String getProfileId() { return profileId; }
    public SendVerificationRequest setProfileId(String profileId) { this.profileId = profileId; return this; }
    public String getAppName() { return appName; }
    public SendVerificationRequest setAppName(String appName) { this.appName = appName; return this; }
    public Integer getTimeoutSecs() { return timeoutSecs; }
    public SendVerificationRequest setTimeoutSecs(Integer timeoutSecs) { this.timeoutSecs = timeoutSecs; return this; }
    public Integer getCodeLength() { return codeLength; }
    public SendVerificationRequest setCodeLength(Integer codeLength) { this.codeLength = codeLength; return this; }
}
