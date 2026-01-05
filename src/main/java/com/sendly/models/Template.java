package com.sendly.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Template {
    private String id;
    private String name;
    private String text;
    private List<TemplateVariable> variables;
    @SerializedName("is_preset")
    private boolean isPreset;
    @SerializedName("preset_slug")
    private String presetSlug;
    private String status;
    private int version;
    @SerializedName("published_at")
    private String publishedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getText() { return text; }
    public List<TemplateVariable> getVariables() { return variables; }
    public boolean isPreset() { return isPreset; }
    public String getPresetSlug() { return presetSlug; }
    public String getStatus() { return status; }
    public int getVersion() { return version; }
    public String getPublishedAt() { return publishedAt; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    public static class TemplateVariable {
        private String key;
        private String type;
        private String fallback;

        public String getKey() { return key; }
        public String getType() { return type; }
        public String getFallback() { return fallback; }
    }
}
