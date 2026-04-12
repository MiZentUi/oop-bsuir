package controllers

import clients.WeatherDataClient
import models.weather.CurrentWeather
import java.math.BigDecimal

class CurrentWeatherController<T : WeatherDataClient>(
    private val client: T
) {
    fun getCurrentWeather(lat: BigDecimal, lon: BigDecimal): CurrentWeather {
        val temperature = client.locationCurrentTemperature(lat, lon)
        return CurrentWeather(temperature = temperature)
    }
}
