package com.quickmap.ppttovhvideo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ai.server")
@Component
public class AIServerProperties {
    private String soundserver;

    private String digithumanserver;


    private String deepseekserver;

    private String deepseekkey;


    private  String deepseekmodel;

    public String getDeepseekmodel() {
        return deepseekmodel;
    }

    public void setDeepseekmodel(String deepseekmodel) {
        this.deepseekmodel = deepseekmodel;
    }

    public String getDigithumanserver() {
        return digithumanserver;
    }

    public void setDigithumanserver(String digithumanserver) {
        this.digithumanserver = digithumanserver;
    }

    public String getSoundserver() {
        return soundserver;
    }

    public void setSoundserver(String soundserver) {
        this.soundserver = soundserver;
    }

    public void setDeepseekserver(String deepseekserver) {
        this.deepseekserver = deepseekserver;
    }

    public void setDeepseekkey(String deepseekkey) {
        this.deepseekkey = deepseekkey;
    }

    public String getDeepseekserver() {
        return deepseekserver;
    }

    public String getDeepseekkey() {
        return deepseekkey;
    }
}


 