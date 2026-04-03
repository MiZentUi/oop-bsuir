package com.forecast.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.forecast.client.WeatherDataClient;
import com.forecast.model.CurrentWeather;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentWeatherService {
    private final WeatherDataClient client;

    public CurrentWeather getCurrentWeather(BigDecimal lat, BigDecimal lon) {
        try {
            BigDecimal temperature = client.getCurrentWeather(lat, lon);
            return new CurrentWeather(temperature);
        } catch (Exception e) {
            return null;
        }
    }
}
