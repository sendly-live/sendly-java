package com.sendly.models;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Response from {@code rcs().agents().setTestDevices()}: the full device
 * list after the change.
 */
public class RcsTestDevicesResponse {
    private List<RcsTestDevice> devices;

    public RcsTestDevicesResponse() {
        this.devices = new ArrayList<>();
    }

    public RcsTestDevicesResponse(JsonObject json) {
        this.devices = new ArrayList<>();
        List<JsonObject> devices = RcsJson.objects(json, "devices");
        if (devices != null) {
            for (JsonObject d : devices) {
                this.devices.add(new RcsTestDevice(d));
            }
        }
    }

    /** The agent's test devices after the change. */
    public List<RcsTestDevice> getDevices() { return devices; }
}
