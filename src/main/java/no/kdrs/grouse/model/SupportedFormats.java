package no.kdrs.grouse.model;

import java.util.Map;

public class SupportedFormats {
    Map<String, String> supportedFormats;

    public SupportedFormats(Map<String, String> supportedFormats) {
        this.supportedFormats = supportedFormats;
    }

    public Map<String, String> getSupportedFormats() {
        return supportedFormats;
    }

    public void setSupportedFormats(Map<String, String> supportedFormats) {
        this.supportedFormats = supportedFormats;
    }
}
