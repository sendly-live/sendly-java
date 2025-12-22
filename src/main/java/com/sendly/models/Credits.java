package com.sendly.models;

import com.google.gson.JsonObject;

/**
 * Represents credit balance information.
 */
public class Credits {
    private final int balance;
    private final int reservedBalance;
    private final int availableBalance;

    public Credits(JsonObject json) {
        this.balance = getIntOrDefault(json, "balance", 0);
        this.reservedBalance = getIntOrDefault(json, "reserved_balance", "reservedBalance", 0);
        this.availableBalance = getIntOrDefault(json, "available_balance", "availableBalance", 0);
    }

    private int getIntOrDefault(JsonObject json, String key, int defaultVal) {
        return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsInt() : defaultVal;
    }

    private int getIntOrDefault(JsonObject json, String key1, String key2, int defaultVal) {
        if (json.has(key1) && !json.get(key1).isJsonNull()) return json.get(key1).getAsInt();
        if (json.has(key2) && !json.get(key2).isJsonNull()) return json.get(key2).getAsInt();
        return defaultVal;
    }

    public int getBalance() { return balance; }
    public int getReservedBalance() { return reservedBalance; }
    public int getAvailableBalance() { return availableBalance; }

    @Override
    public String toString() {
        return "Credits{balance=" + balance + ", available=" + availableBalance + "}";
    }
}
