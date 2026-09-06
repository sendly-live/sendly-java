package com.sendly.models;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Response from {@code rcs().registration().get()}: the workspace's newest
 * agent, its brand and test devices, and where the registration stands.
 */
public class RcsRegistration {
    private RcsBrand brand;
    private RcsAgentDetails agent;
    private List<RcsTestDevice> devices;
    private String stage;
    private boolean usEligible;

    public RcsRegistration() {
        this.devices = new ArrayList<>();
    }

    public RcsRegistration(JsonObject json) {
        JsonObject brand = RcsJson.object(json, "brand");
        this.brand = brand != null ? new RcsBrand(brand) : null;
        JsonObject agent = RcsJson.object(json, "agent");
        this.agent = agent != null ? new RcsAgentDetails(agent) : null;
        this.devices = new ArrayList<>();
        List<JsonObject> devices = RcsJson.objects(json, "devices");
        if (devices != null) {
            for (JsonObject d : devices) {
                this.devices.add(new RcsTestDevice(d));
            }
        }
        this.stage = RcsJson.string(json, "stage");
        Boolean usEligible = RcsJson.bool(json, "usEligible");
        this.usEligible = usEligible != null && usEligible;
    }

    /** The newest agent's brand (else the newest brand), or null when none exists. */
    public RcsBrand getBrand() { return brand; }

    /** The newest agent, or null when none exists. */
    public RcsAgentDetails getAgent() { return agent; }

    /** That agent's test devices; empty when there is no agent. */
    public List<RcsTestDevice> getDevices() { return devices; }

    /** Where the registration stands; see {@link RcsCustomerStage}. "draft" when nothing exists. */
    public String getStage() { return stage; }

    /** False when something on file names a non-US country. */
    public boolean isUsEligible() { return usEligible; }
}
