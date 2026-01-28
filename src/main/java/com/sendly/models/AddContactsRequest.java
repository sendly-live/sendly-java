package com.sendly.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AddContactsRequest {
    @SerializedName("contact_ids")
    private final List<String> contactIds;

    public AddContactsRequest(List<String> contactIds) {
        this.contactIds = contactIds;
    }

    public List<String> getContactIds() { return contactIds; }
}
