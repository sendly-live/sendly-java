package com.sendly.models;

/**
 * Values accepted by {@code RcsBrandInput.Builder#organizationType(String)}.
 */
public final class RcsOrganizationType {
    public static final String PRIVATE_PROFIT = "PRIVATE_PROFIT";
    public static final String PUBLIC_PROFIT = "PUBLIC_PROFIT";
    public static final String NON_PROFIT = "NON_PROFIT";
    public static final String GOVERNMENT = "GOVERNMENT";
    public static final String UNKNOWN = "UNKNOWN";

    private RcsOrganizationType() {}
}
