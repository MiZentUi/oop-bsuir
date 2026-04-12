package com.forecast.clients;

import java.math.BigDecimal;

public interface WeatherDataClient {
    BigDecimal locationCurrentTemperature(BigDecimal lat, BigDecimal lon) throws Exception;
}
