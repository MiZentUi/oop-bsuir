package com.forecast.models.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CurrentWeather {
    @JsonProperty("temperature")
    private BigDecimal temperature;

    public CurrentWeather() {
    }

    public CurrentWeather(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }
}
