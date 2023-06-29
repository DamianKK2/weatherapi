package com.example.weatherapi.api.weather;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Response(Location location, WeatherAttributes current) {
    
}
