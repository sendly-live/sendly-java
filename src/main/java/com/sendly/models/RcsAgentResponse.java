package com.sendly.models;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Response from {@code rcs().agents().create()}, {@code get()},
 * {@code update()}, {@code submit()} and {@code requestLaunch()}.
 */
public class RcsAgentResponse {
    private RcsAgentDetails agent;
    private List<RcsTestDevice> devices;
    private String stage;

    public RcsAgentResponse() {
        this.devices = new ArrayList<>();
    }

    public RcsAgentResponse(JsonObject json) {
        JsonObject agent = RcsJson.object(json, "agent");
        this.agent = agent != null ? new RcsAgentDetails(agent) : null;
        List<JsonObject> devices = RcsJson.objects(json, "devices");
        if (devices != null) {
            this.devices = new ArrayList<>();
            for (JsonObject d : devices) {
                this.devices.add(new RcsTestDevice(d));
            }
        } else {
            this.devices = this.agent != null ? this.agent.getTestDevices() : new ArrayList<>();
        }
        String stage = RcsJson.string(json, "stage");
        this.stage = stage != null ? stage : (this.agent != null ? this.agent.getCustomerStage() : null);
    }

    /** The agent as stored. */
    public RcsAgentDetails getAgent() { return agent; }

    /** The agent's test devices. */
    public List<RcsTestDevice> getDevices() { return devices; }

    /** Where the agent sits in the registration journey; see {@link RcsCustomerStage}. */
    public String getStage() { return stage; }
}
