package com.example.weatherapi.service;

import org.springframework.http.ResponseEntity;

import com.example.weatherapi.model.City;
import com.example.weatherapi.model.Weather;

public interface WeatherService {
    ResponseEntity<Weather> findCurrentWeather(City city);
}
