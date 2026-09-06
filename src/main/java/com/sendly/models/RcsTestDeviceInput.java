package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * A phone to invite as a test device, passed to
 * {@code rcs().agents().setTestDevices(...)}.
 */
public class RcsTestDeviceInput {
    private final String phoneNumber;
    private final String label;

    /**
     * @param phoneNumber E.164 number, or a 10-digit US number
     */
    public RcsTestDeviceInput(String phoneNumber) {
        this(phoneNumber, null);
    }

    /**
     * @param phoneNumber E.164 number, or a 10-digit US number
     * @param label       A label for the device (e.g. "Sam's phone"), or null
     */
    public RcsTestDeviceInput(String phoneNumber, String label) {
        this.phoneNumber = phoneNumber;
        this.label = label;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public String getLabel() { return label; }

    public JsonObject toJson() {
        JsonObject o = new JsonObject();
        RcsJson.put(o, "phoneNumber", phoneNumber);
        RcsJson.put(o, "label", label);
        return o;
    }
}
