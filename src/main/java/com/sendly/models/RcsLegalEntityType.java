package com.sendly.models;

/**
 * Values accepted by {@code RcsBrandInput.Builder#legalEntityType(String)}.
 */
public final class RcsLegalEntityType {
    public static final String LIMITED_LIABILITY_COMPANY = "LIMITED_LIABILITY_COMPANY";
    public static final String SOLE_PROPRIETORSHIP = "SOLE_PROPRIETORSHIP";
    public static final String PARTNERSHIP = "PARTNERSHIP";
    public static final String CORPORATION = "CORPORATION";
    public static final String S_CORPORATION = "S_CORPORATION";

    private RcsLegalEntityType() {}
}
