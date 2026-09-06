package com.sendly.models;

/**
 * Values accepted for {@link RcsOptInMethod#getMethodType()}.
 */
public final class RcsOptInMethodType {
    public static final String SMS = "SMS";
    public static final String WEBSITE = "WEBSITE";
    public static final String MOBILE_APP = "MOBILE_APP";
    public static final String QR_CODE = "QR_CODE";
    public static final String SALE_POINT = "SALE_POINT";
    public static final String OTHER = "OTHER";

    private RcsOptInMethodType() {}
}
