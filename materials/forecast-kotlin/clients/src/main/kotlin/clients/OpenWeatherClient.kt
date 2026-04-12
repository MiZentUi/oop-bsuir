package clients

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
private data class OpenWeatherResponse(
    @JsonProperty("main") val main: MainBlock
)

@JsonIgnoreProperties(ignoreUnknown = true)
private data class MainBlock(
    @JsonProperty("temp") val temp: BigDecimal
)

class OpenWeatherClient(
    private val apiKey: String,
    private val baseURL: String
) : WeatherDataClient {

    private val http = OkHttpClient()
    private val mapper = jacksonObjectMapper()

    override fun locationCurrentTemperature(lat: BigDecimal, lon: BigDecimal): BigDecimal {
        val url = "$baseURL?lat=$lat&lon=$lon&appid=$apiKey&units=metric"

        val request = Request.Builder().url(url).get().build()

        http.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("openweather returned bad status: ${response.code}")
            }

            val body = response.body?.string()
                ?: throw RuntimeException("failed to read response body")

            val data: OpenWeatherResponse = mapper.readValue(body)
            return data.main.temp
        }
    }
}
