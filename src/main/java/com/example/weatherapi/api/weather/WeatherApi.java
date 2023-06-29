package com.example.weatherapi.api.weather;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class WeatherApi {
    
    private static final String REQUEST_URL = "/current.json?key={apiKey}&q={city}&aqi=no";

	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApi.class);

    @Value("${api.weather.url}")
    private String apiUrl;

    @Value("${api.weather.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Response> findCurrentWeather(String city) throws RestClientException {

        Response object = restTemplate.getForObject(apiUrl + REQUEST_URL, 
            Response.class, apiKey, city);
        LOGGER.info(object.toString());

        return ResponseEntity.ok(object);
    }
}
