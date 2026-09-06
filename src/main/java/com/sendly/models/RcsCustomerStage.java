package com.sendly.models;

/**
 * Where a brand or agent sits in the RCS registration journey, as reported
 * by {@code getCustomerStage()} on brands and agents and by the top-level
 * {@code stage} on registration, agent, submit and launch responses.
 * <p>
 * Stages are plain strings so a stage introduced later still decodes; compare
 * against these constants.
 * </p>
 */
public final class RcsCustomerStage {
    /** Nothing submitted yet, or edits in progress. */
    public static final String DRAFT = "draft";
    /** Submitted; Sendly is reviewing the brand and agent basics. */
    public static final String IN_REVIEW = "in_review";
    /** Sendly asked for changes; see {@code getReviewNote()}. */
    public static final String CHANGES_REQUESTED = "changes_requested";
    /** Sendly declined the registration. */
    public static final String REJECTED = "rejected";
    /** The brand is with the carrier network for verification. */
    public static final String BRAND_VERIFICATION = "brand_verification";
    /** The agent is with the carrier network for review. */
    public static final String AGENT_REVIEW = "agent_review";
    /** Approved for testing; invited test devices can receive messages. */
    public static final String TESTING = "testing";
    /** Launch requested; Sendly is reviewing the campaign details. */
    public static final String LAUNCH_REVIEW = "launch_review";
    /** Launch submitted to the carrier network. */
    public static final String LAUNCHING = "launching";
    /** The carrier network declined the launch; see {@code getRejectionReason()}. */
    public static final String LAUNCH_REJECTED = "launch_rejected";
    /** Live: the agent reaches every RCS-capable recipient. */
    public static final String LIVE = "live";
    /** Suspended. */
    public static final String SUSPENDED = "suspended";
    /** Failed. */
    public static final String FAILED = "failed";

    private RcsCustomerStage() {}
}
