package com.quickmap.ppttovhvideo.bean;

public class PPTBody {
    private String content;
    private String language;
    private String model;
    private Boolean stream;

    public PPTBody(String content, String language, String model, Boolean stream) {
        this.content = content;
        this.language = language;
        this.model = model;
        this.stream = stream;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
}
