package clients

import java.math.BigDecimal

interface WeatherDataClient {
    fun locationCurrentTemperature(lat: BigDecimal, lon: BigDecimal): BigDecimal
}