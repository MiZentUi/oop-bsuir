package com.forecast.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.openweater")
public class OpenWeaterProperties {
    private String baseUrl;
    private String apiKey;
}
