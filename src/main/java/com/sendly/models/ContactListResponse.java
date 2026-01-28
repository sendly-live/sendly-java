package com.sendly.models;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.ArrayList;

public class ContactListResponse {
    private List<Contact> contacts;
    private int total;
    private int limit;
    private int offset;

    public ContactListResponse() {
        this.contacts = new ArrayList<>();
    }

    public ContactListResponse(JsonObject json) {
        this.contacts = new ArrayList<>();
        if (json.has("contacts") && json.get("contacts").isJsonArray()) {
            json.get("contacts").getAsJsonArray().forEach(e ->
                contacts.add(new Contact(e.getAsJsonObject()))
            );
        }
        if (json.has("total")) this.total = json.get("total").getAsInt();
        if (json.has("limit")) this.limit = json.get("limit").getAsInt();
        if (json.has("offset")) this.offset = json.get("offset").getAsInt();
    }

    public List<Contact> getContacts() { return contacts; }
    public int getTotal() { return total; }
    public int getLimit() { return limit; }
    public int getOffset() { return offset; }
}
