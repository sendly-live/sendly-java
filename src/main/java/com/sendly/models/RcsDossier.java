package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Response from {@code rcs().dossier().get()}: brand fields prefilled from
 * what the workspace has already registered, ready to complete and pass to
 * {@code rcs().brands().create(...)}.
 */
public class RcsDossier {
    /** Prefilled from the workspace's newest 10DLC brand. */
    public static final String SOURCE_TENDLC = "tendlc";
    /** Prefilled from the active toll-free verification. */
    public static final String SOURCE_VERIFICATION = "verification";
    /** Nothing on file; the brand is empty. */
    public static final String SOURCE_NONE = "none";

    private RcsBrandInput brand;
    private boolean usEligible;
    private String source;

    public RcsDossier() {}

    public RcsDossier(JsonObject json) {
        JsonObject brand = RcsJson.object(json, "brand");
        this.brand = new RcsBrandInput(brand != null ? brand : new JsonObject());
        Boolean usEligible = RcsJson.bool(json, "usEligible");
        this.usEligible = usEligible != null && usEligible;
        this.source = RcsJson.string(json, "source");
    }

    /** The prefilled brand fields; only non-empty fields are set. */
    public RcsBrandInput getBrand() { return brand; }

    /** False when something on file names a non-US country. */
    public boolean isUsEligible() { return usEligible; }

    /** Where the prefill came from: {@link #SOURCE_TENDLC}, {@link #SOURCE_VERIFICATION} or {@link #SOURCE_NONE}. */
    public String getSource() { return source; }
}
