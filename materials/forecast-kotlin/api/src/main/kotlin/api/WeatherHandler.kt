package api

import clients.OpenWeatherClient
import controllers.CurrentWeatherController
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.weather.CurrentWeather
import shared.responses.StatusResponse
import shared.responses.SuccessResponse
import shared.utils.getEnv
import java.math.BigDecimal

class WeatherHandler(
    val controller: CurrentWeatherController<OpenWeatherClient> =
        CurrentWeatherController(
            OpenWeatherClient(
                apiKey = getEnv("OPENWEATHER_API_KEY", ""),
                baseURL = getEnv("OPENWEATHER_BASE_URL", "")
            )
        )
)

fun Route.weatherRoutes(handler: WeatherHandler = WeatherHandler()) {
    route("/weather") {

        get({
            summary = "Get Current Weather"
            description = "Returns current weather for given coordinates"
            tags = listOf("weather")
            request {
                queryParameter<String>("lat") {
                    description = "Latitude"
                    required = true
                    example("default") { value = "18.300231990440125" }
                }
                queryParameter<String>("lon") {
                    description = "Longitude"
                    required = true
                    example("default") { value = "-64.8251590359234" }
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "OK"
                    body<SuccessResponse<CurrentWeather>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Bad Request"
                    body<StatusResponse>()
                }
                HttpStatusCode.InternalServerError to {
                    description = "Internal Server Error"
                    body<StatusResponse>()
                }
            }
        }) {
            val latRaw = call.request.queryParameters["lat"]
            val lonRaw = call.request.queryParameters["lon"]

            val lat = latRaw?.toBigDecimalOrNull()
            val lon = lonRaw?.toBigDecimalOrNull()

            if (lat == null || lon == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    StatusResponse(code = 400, message = "invalid coordinates")
                )
                return@get
            }

            val result: CurrentWeather = try {
                handler.controller.getCurrentWeather(lat, lon)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    StatusResponse(code = 500, message = e.message ?: "internal error")
                )
                return@get
            }

            call.respond(
                HttpStatusCode.OK,
                SuccessResponse(code = 200, message = "Success", data = result)
            )
        }
    }
}
