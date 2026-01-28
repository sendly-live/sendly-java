package com.sendly.models;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.ArrayList;

public class CampaignList {
    private List<Campaign> campaigns;
    private int total;
    private int limit;
    private int offset;

    public CampaignList() {
        this.campaigns = new ArrayList<>();
    }

    public CampaignList(JsonObject json) {
        this.campaigns = new ArrayList<>();
        if (json.has("campaigns") && json.get("campaigns").isJsonArray()) {
            json.get("campaigns").getAsJsonArray().forEach(e ->
                campaigns.add(new Campaign(e.getAsJsonObject()))
            );
        }
        if (json.has("total")) this.total = json.get("total").getAsInt();
        if (json.has("limit")) this.limit = json.get("limit").getAsInt();
        if (json.has("offset")) this.offset = json.get("offset").getAsInt();
    }

    public List<Campaign> getCampaigns() { return campaigns; }
    public int getTotal() { return total; }
    public int getLimit() { return limit; }
    public int getOffset() { return offset; }
}
