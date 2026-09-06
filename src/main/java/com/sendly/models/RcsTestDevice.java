package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * A phone invited to receive messages from an agent while it is in testing.
 */
public class RcsTestDevice {
    private String id;
    private String phoneNumber;
    private String label;
    private String inviteStatus;
    private String createdAt;

    public RcsTestDevice() {}

    public RcsTestDevice(JsonObject json) {
        this.id = RcsJson.string(json, "id");
        this.phoneNumber = RcsJson.string(json, "phoneNumber");
        this.label = RcsJson.string(json, "label");
        this.inviteStatus = RcsJson.string(json, "inviteStatus");
        this.createdAt = RcsJson.string(json, "createdAt");
    }

    /** Unique device identifier. */
    public String getId() { return id; }

    /** The device's number in E.164 format. */
    public String getPhoneNumber() { return phoneNumber; }

    /** Label you gave the device, or null. */
    public String getLabel() { return label; }

    /** Invite state reported by the carrier network (e.g. "PENDING"); null until invited. */
    public String getInviteStatus() { return inviteStatus; }

    /** When the device was added (ISO-8601). */
    public String getCreatedAt() { return createdAt; }
}
