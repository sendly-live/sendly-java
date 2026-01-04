package com.sendly.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Response from previewing a batch (dry run).
 */
public class BatchPreviewResponse {
    private final boolean canSend;
    private final int totalMessages;
    private final int willSend;
    private final int blocked;
    private final int creditsNeeded;
    private final int currentBalance;
    private final boolean hasEnoughCredits;
    private final List<BatchPreviewItem> messages;
    private final Map<String, Integer> blockReasons;

    /**
     * Create a BatchPreviewResponse from a JSON object.
     */
    public BatchPreviewResponse(JsonObject json) {
        this.canSend = json.has("canSend") && json.get("canSend").getAsBoolean();
        this.totalMessages = json.has("totalMessages") ? json.get("totalMessages").getAsInt() : 0;
        this.willSend = json.has("willSend") ? json.get("willSend").getAsInt() : 0;
        this.blocked = json.has("blocked") ? json.get("blocked").getAsInt() : 0;
        this.creditsNeeded = json.has("creditsNeeded") ? json.get("creditsNeeded").getAsInt() : 0;
        this.currentBalance = json.has("currentBalance") ? json.get("currentBalance").getAsInt() : 0;
        this.hasEnoughCredits = json.has("hasEnoughCredits") && json.get("hasEnoughCredits").getAsBoolean();

        this.messages = new ArrayList<>();
        if (json.has("messages") && json.get("messages").isJsonArray()) {
            JsonArray messagesArray = json.getAsJsonArray("messages");
            for (JsonElement element : messagesArray) {
                messages.add(new BatchPreviewItem(element.getAsJsonObject()));
            }
        }

        this.blockReasons = new HashMap<>();
        if (json.has("blockReasons") && json.get("blockReasons").isJsonObject()) {
            JsonObject reasons = json.getAsJsonObject("blockReasons");
            for (String key : reasons.keySet()) {
                blockReasons.put(key, reasons.get(key).getAsInt());
            }
        }
    }

    // Getters

    public boolean canSend() {
        return canSend;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public int getWillSend() {
        return willSend;
    }

    public int getBlocked() {
        return blocked;
    }

    public int getCreditsNeeded() {
        return creditsNeeded;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public boolean hasEnoughCredits() {
        return hasEnoughCredits;
    }

    public List<BatchPreviewItem> getMessages() {
        return messages;
    }

    public Map<String, Integer> getBlockReasons() {
        return blockReasons;
    }

    @Override
    public String toString() {
        return "BatchPreviewResponse{" +
                "canSend=" + canSend +
                ", totalMessages=" + totalMessages +
                ", willSend=" + willSend +
                ", blocked=" + blocked +
                ", creditsNeeded=" + creditsNeeded +
                ", currentBalance=" + currentBalance +
                ", hasEnoughCredits=" + hasEnoughCredits +
                '}';
    }

    /**
     * Represents a single message in a batch preview.
     */
    public static class BatchPreviewItem {
        private final String to;
        private final String text;
        private final int segments;
        private final int credits;
        private final boolean canSend;
        private final String blockReason;
        private final String country;
        private final String pricingTier;

        public BatchPreviewItem(JsonObject json) {
            this.to = getStringOrNull(json, "to");
            this.text = getStringOrNull(json, "text");
            this.segments = json.has("segments") ? json.get("segments").getAsInt() : 1;
            this.credits = json.has("credits") ? json.get("credits").getAsInt() : 0;
            this.canSend = json.has("canSend") && json.get("canSend").getAsBoolean();
            this.blockReason = getStringOrNull(json, "blockReason");
            this.country = getStringOrNull(json, "country");
            this.pricingTier = getStringOrNull(json, "pricingTier");
        }

        private String getStringOrNull(JsonObject json, String key) {
            return json.has(key) && !json.get(key).isJsonNull() ? json.get(key).getAsString() : null;
        }

        public String getTo() {
            return to;
        }

        public String getText() {
            return text;
        }

        public int getSegments() {
            return segments;
        }

        public int getCredits() {
            return credits;
        }

        public boolean canSend() {
            return canSend;
        }

        public String getBlockReason() {
            return blockReason;
        }

        public String getCountry() {
            return country;
        }

        public String getPricingTier() {
            return pricingTier;
        }

        @Override
        public String toString() {
            return "BatchPreviewItem{" +
                    "to='" + to + '\'' +
                    ", segments=" + segments +
                    ", credits=" + credits +
                    ", canSend=" + canSend +
                    '}';
        }
    }
}
