package com.sendly.models;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An RCS agent in full: identity, campaign, testing details, review state
 * and test devices. Returned by the registration endpoints; the lighter
 * {@link RcsAgent} is what {@code rcs().agents().list()} returns.
 * <p>
 * {@link #getStatus()} is the send status ({@code draft}, {@code submitted},
 * {@code testing}, {@code approved}, {@code suspended});
 * {@link #getReviewStatus()} is one of {@link RcsReviewStatus} and
 * {@link #getCustomerStage()} one of {@link RcsCustomerStage}.
 * </p>
 */
public class RcsAgentDetails {
    private String id;
    private String brandId;
    private String status;
    private String reviewStatus;
    private String customerStage;
    private String displayName;
    private String useCase;
    private String hostingRegion;
    private RcsAgentBasics basics;
    private RcsCampaign campaign;
    private RcsTesting testing;
    private String reviewNote;
    private String rejectionReason;
    private List<RcsTestDevice> testDevices;
    private String submittedForReviewAt;
    private String basicsSubmittedAt;
    private String launchSubmittedAt;
    private String liveAt;
    private String createdAt;
    private String updatedAt;

    public RcsAgentDetails() {
        this.testDevices = new ArrayList<>();
    }

    public RcsAgentDetails(JsonObject json) {
        this.id = RcsJson.string(json, "id");
        this.brandId = RcsJson.string(json, "brandId");
        this.status = RcsJson.string(json, "status");
        this.reviewStatus = RcsJson.string(json, "reviewStatus");
        this.customerStage = RcsJson.string(json, "customerStage");
        this.displayName = RcsJson.string(json, "displayName");
        this.useCase = RcsJson.string(json, "useCase");
        this.hostingRegion = RcsJson.string(json, "hostingRegion");
        JsonObject basics = RcsJson.object(json, "basics");
        this.basics = basics != null ? new RcsAgentBasics(basics) : null;
        JsonObject campaign = RcsJson.object(json, "campaign");
        this.campaign = campaign != null ? new RcsCampaign(campaign) : null;
        JsonObject testing = RcsJson.object(json, "testing");
        this.testing = testing != null ? new RcsTesting(testing) : null;
        this.reviewNote = RcsJson.string(json, "reviewNote");
        this.rejectionReason = RcsJson.string(json, "rejectionReason");
        this.testDevices = new ArrayList<>();
        List<JsonObject> devices = RcsJson.objects(json, "testDevices");
        if (devices != null) {
            for (JsonObject d : devices) {
                this.testDevices.add(new RcsTestDevice(d));
            }
        }
        this.submittedForReviewAt = RcsJson.string(json, "submittedForReviewAt");
        this.basicsSubmittedAt = RcsJson.string(json, "basicsSubmittedAt");
        this.launchSubmittedAt = RcsJson.string(json, "launchSubmittedAt");
        this.liveAt = RcsJson.string(json, "liveAt");
        this.createdAt = RcsJson.string(json, "createdAt");
        this.updatedAt = RcsJson.string(json, "updatedAt");
    }

    /** Unique agent identifier; pass as {@code agentId} on sends. */
    public String getId() { return id; }

    /** The brand this agent belongs to, or null. */
    public String getBrandId() { return brandId; }

    /** Send status: "draft", "submitted", "testing", "approved" or "suspended". */
    public String getStatus() { return status; }

    /** Review status; see {@link RcsReviewStatus}. */
    public String getReviewStatus() { return reviewStatus; }

    /** Where the agent sits in the registration journey; see {@link RcsCustomerStage}. */
    public String getCustomerStage() { return customerStage; }

    /** The agent name recipients see. */
    public String getDisplayName() { return displayName; }

    /** Use case ({@link RcsAgentUseCase}), or null. */
    public String getUseCase() { return useCase; }

    /** Hosting region, set by Sendly; null on a draft. */
    public String getHostingRegion() { return hostingRegion; }

    /** Identity shown on the info card. */
    public RcsAgentBasics getBasics() { return basics; }

    /** Campaign details, or null until set. */
    public RcsCampaign getCampaign() { return campaign; }

    /** Testing details, or null until set. */
    public RcsTesting getTesting() { return testing; }

    /** Sendly's note when changes were requested, or null. */
    public String getReviewNote() { return reviewNote; }

    /** Why the carrier network rejected the agent or its launch, or null. */
    public String getRejectionReason() { return rejectionReason; }

    /** Phones invited to receive messages while the agent is in testing. */
    public List<RcsTestDevice> getTestDevices() { return testDevices; }

    /** When the agent was submitted for review (ISO-8601), or null. */
    public String getSubmittedForReviewAt() { return submittedForReviewAt; }

    /** When the basics were sent to the carrier network (ISO-8601), or null. */
    public String getBasicsSubmittedAt() { return basicsSubmittedAt; }

    /** When the launch was sent to the carrier network (ISO-8601), or null. */
    public String getLaunchSubmittedAt() { return launchSubmittedAt; }

    /** When the agent went live (ISO-8601), or null. */
    public String getLiveAt() { return liveAt; }

    /** When the agent was created (ISO-8601). */
    public String getCreatedAt() { return createdAt; }

    /** When the agent was last updated (ISO-8601). */
    public String getUpdatedAt() { return updatedAt; }
}
