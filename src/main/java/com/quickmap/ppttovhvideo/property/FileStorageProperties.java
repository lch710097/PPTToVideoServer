package com.quickmap.ppttovhvideo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")

public class FileStorageProperties {
    private String uploadDir;

    private String voiceDir;

    private String digithumanDir;

    private String genVedioDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public void setVoiceDir(String voiceDir) {
        this.voiceDir = voiceDir;
    }

    public void setDigithumanDir(String digithumanDir) {
        this.digithumanDir = digithumanDir;
    }

    public void setGenVedioDir(String genVedioDir) {
        this.genVedioDir = genVedioDir;
    }

    public String getVoiceDir() {
        return voiceDir;
    }

    public String getDigithumanDir() {
        return digithumanDir;
    }

    public String getGenVedioDir() {
        return genVedioDir;
    }
}


