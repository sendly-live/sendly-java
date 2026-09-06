package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * An RCS brand: the business identity behind one or more agents.
 * <p>
 * {@link #getReviewStatus()} is one of the {@link RcsReviewStatus} values and
 * {@link #getCustomerStage()} one of the {@link RcsCustomerStage} values.
 * While the brand is {@code awaiting_review} or {@code launch_requested} its
 * fields are locked (409 {@code rcs_field_locked}); once Sendly asks for
 * changes, {@link #getReviewNote()} says what to fix.
 * </p>
 */
public class RcsBrand {
    private String id;
    private String reviewStatus;
    private String customerStage;
    private String displayName;
    private String legalName;
    private String legalEntityType;
    private String organizationType;
    private String stockSymbol;
    private String websiteUrl;
    private String ein;
    private RcsBrandAddress address;
    private RcsBrandContact contact;
    private String reviewNote;
    private String rejectionReason;
    private String submittedForReviewAt;
    private String sentToCarrierAt;
    private String verifiedAt;
    private String createdAt;
    private String updatedAt;

    public RcsBrand() {}

    public RcsBrand(JsonObject json) {
        this.id = RcsJson.string(json, "id");
        this.reviewStatus = RcsJson.string(json, "reviewStatus");
        this.customerStage = RcsJson.string(json, "customerStage");
        this.displayName = RcsJson.string(json, "displayName");
        this.legalName = RcsJson.string(json, "legalName");
        this.legalEntityType = RcsJson.string(json, "legalEntityType");
        this.organizationType = RcsJson.string(json, "organizationType");
        this.stockSymbol = RcsJson.string(json, "stockSymbol");
        this.websiteUrl = RcsJson.string(json, "websiteUrl");
        this.ein = RcsJson.string(json, "ein");
        JsonObject address = RcsJson.object(json, "address");
        this.address = address != null ? new RcsBrandAddress(address) : null;
        JsonObject contact = RcsJson.object(json, "contact");
        this.contact = contact != null ? new RcsBrandContact(contact) : null;
        this.reviewNote = RcsJson.string(json, "reviewNote");
        this.rejectionReason = RcsJson.string(json, "rejectionReason");
        this.submittedForReviewAt = RcsJson.string(json, "submittedForReviewAt");
        this.sentToCarrierAt = RcsJson.string(json, "sentToCarrierAt");
        this.verifiedAt = RcsJson.string(json, "verifiedAt");
        this.createdAt = RcsJson.string(json, "createdAt");
        this.updatedAt = RcsJson.string(json, "updatedAt");
    }

    /** Unique brand identifier; pass as {@code brandId} when creating an agent. */
    public String getId() { return id; }

    /** Review status; see {@link RcsReviewStatus}. */
    public String getReviewStatus() { return reviewStatus; }

    /** Where the brand sits in the registration journey; see {@link RcsCustomerStage}. */
    public String getCustomerStage() { return customerStage; }

    /** The brand name recipients see. */
    public String getDisplayName() { return displayName; }

    /** Registered legal business name. */
    public String getLegalName() { return legalName; }

    /** Legal entity type ({@link RcsLegalEntityType}), or "" on an empty draft. */
    public String getLegalEntityType() { return legalEntityType; }

    /** Organization type ({@link RcsOrganizationType}), or "" on an empty draft. */
    public String getOrganizationType() { return organizationType; }

    /** Stock symbol as "EXCHANGE:TICKER", or null. */
    public String getStockSymbol() { return stockSymbol; }

    /** Business website. */
    public String getWebsiteUrl() { return websiteUrl; }

    /** EIN. */
    public String getEin() { return ein; }

    /** Business address. */
    public RcsBrandAddress getAddress() { return address; }

    /** Brand contact. */
    public RcsBrandContact getContact() { return contact; }

    /** Sendly's note when changes were requested, or null. */
    public String getReviewNote() { return reviewNote; }

    /** Why the carrier network rejected the brand, or null. */
    public String getRejectionReason() { return rejectionReason; }

    /** When the brand was submitted for review (ISO-8601), or null. */
    public String getSubmittedForReviewAt() { return submittedForReviewAt; }

    /** When the brand was sent to the carrier network (ISO-8601), or null. */
    public String getSentToCarrierAt() { return sentToCarrierAt; }

    /** When the carrier network verified the brand (ISO-8601), or null. */
    public String getVerifiedAt() { return verifiedAt; }

    /** When the brand was created (ISO-8601). */
    public String getCreatedAt() { return createdAt; }

    /** When the brand was last updated (ISO-8601). */
    public String getUpdatedAt() { return updatedAt; }
}
