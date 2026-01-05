package com.sendly.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TemplatePreview {
    private String id;
    private String name;
    @SerializedName("original_text")
    private String originalText;
    @SerializedName("preview_text")
    private String previewText;
    private List<Template.TemplateVariable> variables;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getOriginalText() { return originalText; }
    public String getPreviewText() { return previewText; }
    public List<Template.TemplateVariable> getVariables() { return variables; }
}
