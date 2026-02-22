package com.forecast.controllers.weather;

import com.forecast.clients.WeatherDataClient;
import com.forecast.models.weather.CurrentWeather;
import java.math.BigDecimal;

public class CurrentWeatherController {
    private final WeatherDataClient client;

    public CurrentWeatherController(WeatherDataClient client) {
        this.client = client;
    }

    public CurrentWeather getCurrentWeather(BigDecimal lat, BigDecimal lon) {
        try {
            BigDecimal temperature = client.locationCurrentTemperature(lat, lon);
            return new CurrentWeather(temperature);
        } catch (Exception e) {
            return null;
        }
    }
}
