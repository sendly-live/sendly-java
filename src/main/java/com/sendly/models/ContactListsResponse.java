package com.sendly.models;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.ArrayList;

public class ContactListsResponse {
    private List<ContactList> lists;
    private int total;
    private int limit;
    private int offset;

    public ContactListsResponse() {
        this.lists = new ArrayList<>();
    }

    public ContactListsResponse(JsonObject json) {
        this.lists = new ArrayList<>();
        if (json.has("lists") && json.get("lists").isJsonArray()) {
            json.get("lists").getAsJsonArray().forEach(e ->
                lists.add(new ContactList(e.getAsJsonObject()))
            );
        }
        if (json.has("total")) this.total = json.get("total").getAsInt();
        if (json.has("limit")) this.limit = json.get("limit").getAsInt();
        if (json.has("offset")) this.offset = json.get("offset").getAsInt();
    }

    public List<ContactList> getLists() { return lists; }
    public int getTotal() { return total; }
    public int getLimit() { return limit; }
    public int getOffset() { return offset; }
}
