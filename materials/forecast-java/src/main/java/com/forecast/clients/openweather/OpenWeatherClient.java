package com.forecast.clients.openweather;

import com.forecast.clients.WeatherDataClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OpenWeatherClient implements WeatherDataClient {
    private final String apiKey;
    private final String baseURL;

    public OpenWeatherClient(String apiKey, String baseURL) {
        this.apiKey = apiKey;
        this.baseURL = baseURL;
    }

    @Override
    public BigDecimal locationCurrentTemperature(BigDecimal lat, BigDecimal lon) throws Exception {
        String urlString = String.format("%s?lat=%s&lon=%s&appid=%s&units=metric",
                baseURL, lat.toString(), lon.toString(), apiKey);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("openweather returned bad status: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.toString());
        JsonNode mainNode = jsonNode.get("main");
        
        if (mainNode == null || !mainNode.has("temp")) {
            throw new Exception("failed to decode response: missing temperature data");
        }

        return new BigDecimal(mainNode.get("temp").asText());
    }
}
