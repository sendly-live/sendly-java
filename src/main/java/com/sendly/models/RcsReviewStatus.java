package com.sendly.models;

/**
 * Review status of an RCS brand or agent, as reported by
 * {@code getReviewStatus()}. Plain strings; compare against these constants.
 */
public final class RcsReviewStatus {
    public static final String DRAFT = "draft";
    public static final String AWAITING_REVIEW = "awaiting_review";
    public static final String CHANGES_REQUESTED = "changes_requested";
    public static final String APPROVED_FOR_CARRIER = "approved_for_carrier";
    public static final String REJECTED = "rejected";
    public static final String LAUNCH_REQUESTED = "launch_requested";
    public static final String LAUNCH_SUBMITTED = "launch_submitted";
    public static final String LAUNCH_REJECTED = "launch_rejected";
    public static final String FAILED = "failed";

    private RcsReviewStatus() {}
}
