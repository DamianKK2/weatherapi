package com.example.weatherapi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.example.weatherapi.model.City;
import com.example.weatherapi.model.Weather;
import com.example.weatherapi.service.WeatherService;

public abstract class BaseWeatherService implements WeatherService {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseWeatherService.class);
    
    @Override
    public ResponseEntity<Weather> findCurrentWeather(City city) {
        
        try {
            return findCurrentWeatherByApi(city);
        }
        catch (RestClientException exception) {
            LOGGER.error("** Exception: "+ exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    public abstract ResponseEntity<Weather> findCurrentWeatherByApi(City city) throws RestClientException;
}
