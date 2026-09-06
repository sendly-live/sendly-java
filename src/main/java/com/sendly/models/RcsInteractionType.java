package com.sendly.models;

/**
 * Values accepted for {@link RcsInteraction#getInteractionType()}.
 */
public final class RcsInteractionType {
    public static final String TRANSACTIONAL_UPDATES = "TRANSACTIONAL_UPDATES";
    public static final String CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";
    public static final String LOYALTY_OR_REWARD = "LOYALTY_OR_REWARD";
    public static final String MARKETING_OR_PROMOTIONAL = "MARKETING_OR_PROMOTIONAL";
    public static final String ACCOUNT_ALERTS = "ACCOUNT_ALERTS";
    public static final String TWO_WAY_CONVERSATION = "TWO_WAY_CONVERSATION";
    public static final String OTHER = "OTHER";

    private RcsInteractionType() {}
}
