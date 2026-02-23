package clients

import java.math.BigDecimal

// Mirrors Go: clients.WeatherDataClient interface
interface WeatherDataClient {
    fun locationCurrentTemperature(lat: BigDecimal, lon: BigDecimal): BigDecimal
}