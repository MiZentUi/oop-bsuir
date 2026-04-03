package com.forecast.client;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forecast.property.OpenWeatherProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient implements WeatherDataClient {
    private final OpenWeatherProperties properties;

    @Override
    public BigDecimal getCurrentWeather(BigDecimal lat, BigDecimal lon) throws Exception {
        var restClient = RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();

        var response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", lat.toString())
                        .queryParam("lon", lon.toString())
                        .queryParam("appid", properties.getApiKey())
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .toEntity(String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("openweather returned bad status: " + response.getStatusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        JsonNode mainNode = jsonNode.get("main");

        if (mainNode == null || !mainNode.has("temp")) {
            throw new Exception("failed to decode response: missing temperature data");
        }

        return new BigDecimal(mainNode.get("temp").asText());
    }
}
