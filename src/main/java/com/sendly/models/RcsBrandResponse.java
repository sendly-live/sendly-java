package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Response from {@code rcs().brands().create()} and
 * {@code rcs().brands().update()}.
 */
public class RcsBrandResponse {
    private RcsBrand brand;

    public RcsBrandResponse() {}

    public RcsBrandResponse(JsonObject json) {
        JsonObject brand = RcsJson.object(json, "brand");
        this.brand = brand != null ? new RcsBrand(brand) : null;
    }

    /** The brand as stored. */
    public RcsBrand getBrand() { return brand; }
}
