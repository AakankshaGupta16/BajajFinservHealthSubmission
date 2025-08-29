package com.example.javaqualifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateWebhookResponse {
    @JsonProperty("webhook")
    private String webhookUrl;

    @JsonProperty("accessToken")
    private String accessToken;

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
