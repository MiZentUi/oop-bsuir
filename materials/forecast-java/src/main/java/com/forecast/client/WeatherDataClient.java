package com.forecast.client;

import java.math.BigDecimal;

public interface WeatherDataClient {
    BigDecimal getCurrentWeather(BigDecimal lat, BigDecimal lon) throws Exception;
}
