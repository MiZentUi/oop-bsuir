package com.forecast.api;

import com.forecast.controllers.weather.CurrentWeatherController;
import com.forecast.clients.openweather.OpenWeatherClient;
import com.forecast.shared.utils.EnvUtils;
import com.forecast.shared.responses.StatusResponse;
import com.forecast.shared.responses.SuccessResponse;
import com.forecast.models.weather.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "weather", description = "Weather API")
public class WeatherController {

    private final CurrentWeatherController controller;

    @Autowired
    public WeatherController() {
        String apiKey = EnvUtils.getEnv("OPENWEATHER_API_KEY", "");
        String baseUrl = EnvUtils.getEnv("OPENWEATHER_BASE_URL", "");
        OpenWeatherClient client = new OpenWeatherClient(apiKey, baseUrl);
        this.controller = new CurrentWeatherController(client);
    }

    @GetMapping("/weather")
    @Operation(summary = "Get Current Weather", description = "Returns current weather for given coordinates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getCurrentWeather(
            @Parameter(description = "Latitude", required = true, example = "18.300231990440125")
            @RequestParam String lat,
            @Parameter(description = "Longitude", required = true, example = "-64.8251590359234")
            @RequestParam String lon) {
        
        try {
            BigDecimal latDecimal = new BigDecimal(lat);
            BigDecimal lonDecimal = new BigDecimal(lon);
            
            CurrentWeather result = controller.getCurrentWeather(latDecimal, lonDecimal);
            
            if (result == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusResponse(500, "Failed to get weather data"));
            }
            
            return ResponseEntity.ok(new SuccessResponse<>(200, "Success", result));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StatusResponse(400, "invalid coordinates"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusResponse(500, e.getMessage()));
        }
    }
}
